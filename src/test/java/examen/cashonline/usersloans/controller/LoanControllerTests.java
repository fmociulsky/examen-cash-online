package examen.cashonline.usersloans.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import examen.cashonline.usersloans.UsersLoansApplication;
import examen.cashonline.usersloans.loans.LoanResponseData;
import examen.cashonline.usersloans.loans.entity.Loan;
import examen.cashonline.usersloans.loans.repository.LoanRepository;
import examen.cashonline.usersloans.model.LoanResponseDataTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Paths;

import static examen.cashonline.usersloans.UserLoanDataFixture.LOAN_1;
import static examen.cashonline.usersloans.UserLoanDataFixture.USER_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(classes = UsersLoansApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LoanControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LoanRepository loanRepository;

    @Test
    public void createLoanTest() throws Exception {
        final String loan = new String(Files.readAllBytes(Paths.get(LOAN_1)));
        final MvcResult result = mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(loan)).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Prestamo con Id 1 creado");
    }

    @Test
    public void addLoanToUserOKTest() throws Exception {
        final String user = new String(Files.readAllBytes(Paths.get(USER_1)));
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(user)).andReturn();
        createLoanTest();
        final MvcResult result = mockMvc.perform(put("/loans/{loan_id}/user/{user_id}", 1, 1)).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Prestamo 1 asignado al usuario 1");
        final Loan loan = loanRepository.findById(1L).get();
        assertThat(loan.getUser().getId()).isEqualTo(1);
    }

    @Test
    public void addLoanToUserErrorTest() throws Exception {
        final MvcResult result = mockMvc.perform(put("/loans/{loan_id}/user/{user_id}", 1, 1)).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Usuario 1 o Prestamo 1 no encontrado");
    }

    @Test
    public void findLoansOKTest() throws Exception {
        addLoanToUserOKTest();
        final MvcResult result = mockMvc.perform(get("/loans")
                .param("user_id", "1")
                .param("page", "0")
                .param("size", "2") ).andReturn();
        final LoanResponseDataTest loanResponseData = objectMapper.readValue(result.getResponse().getContentAsString(), LoanResponseDataTest.class);
        assertThat(loanResponseData.getItems().size()).isEqualTo(1);
        assertThat(loanResponseData.getPaging().getPage()).isEqualTo(0);
        assertThat(loanResponseData.getPaging().getSize()).isEqualTo(2);
    }

    @Test
    public void findLoansOK2Test() throws Exception {
        addLoanToUserOKTest();
        final MvcResult result = mockMvc.perform(get("/loans")
                .param("page", "0")
                .param("size", "2") ).andReturn();
        final LoanResponseDataTest loanResponseData = objectMapper.readValue(result.getResponse().getContentAsString(), LoanResponseDataTest.class);
        assertThat(loanResponseData.getItems().size()).isEqualTo(1);
        assertThat(loanResponseData.getPaging().getPage()).isEqualTo(0);
        assertThat(loanResponseData.getPaging().getSize()).isEqualTo(2);
    }

    @Test
    public void findLoansErrorTest() throws Exception {
        addLoanToUserOKTest();

        final MvcResult result = mockMvc.perform(get("/loans")
                .param("user_id", "3")
                .param("page", "0")
                .param("size", "2") ).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Prestamos no encontrados");
    }
}
