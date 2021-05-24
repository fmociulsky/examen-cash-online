package examen.cashonline.usersloans.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import examen.cashonline.usersloans.UsersLoansApplication;
import examen.cashonline.usersloans.loans.repository.LoanRepository;
import examen.cashonline.usersloans.users.entity.User;
import examen.cashonline.usersloans.users.repository.UserRepository;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static examen.cashonline.usersloans.UserLoanDataFixture.LOAN_1;
import static examen.cashonline.usersloans.UserLoanDataFixture.USER_1;
import static examen.cashonline.usersloans.UserLoanDataFixture.USER_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest(classes = UsersLoansApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;


    @Test
    public void createUserTest() throws Exception {
        final String user = new String(Files.readAllBytes(Paths.get(USER_1)));
        final MvcResult result = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(user)).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Usuario con Id 1 creado");
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void findAllUsersTest() throws Exception {
        final String user1 = new String(Files.readAllBytes(Paths.get(USER_1)));
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(user1)).andReturn();
        final String user2 = new String(Files.readAllBytes(Paths.get(USER_2)));
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(user2)).andReturn();
        final MvcResult result = mockMvc.perform(get("/users")).andReturn();
        final List<User> usersFound = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);
        assertThat(usersFound.size()).isEqualTo(2);
    }

    @Test
    public void findUserOKTest() throws Exception {
        createUserTest();
        final MvcResult result = mockMvc.perform(get("/users/{user_id}", 1)).andReturn();
        final User userFound = objectMapper.readValue(result.getResponse().getContentAsString(), User.class);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(userFound.getEmail()).isEqualTo("test@prueba.com.ar");
        assertThat(userFound.getFirstName()).isEqualTo("test");
        assertThat(userFound.getLastName()).isEqualTo("prueba");
        assertThat(userFound.getLoans()).isEmpty();
    }

    @Test
    public void deleteUserOKTest() throws Exception {
        createUserTest();
        final MvcResult result = mockMvc.perform(delete("/users/{user_id}", 1)).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Usuario 1 eliminado");
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(userRepository.findAll()).isEmpty();
    }

    @Test
    public void deleteUserErrorTest() throws Exception {
        createUserTest();
        final MvcResult result = mockMvc.perform(delete("/users/{user_id}", 2)).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Usuario 2 no encontrado");
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(userRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    public void deleteUserWithLoansOKTest() throws Exception {
        createUserTest();
        final String loan = new String(Files.readAllBytes(Paths.get(LOAN_1)));
        mockMvc.perform(post("/loans").contentType(MediaType.APPLICATION_JSON).content(loan)).andReturn();
        mockMvc.perform(put("/loans/{loan_id}/user/{user_id}", 1, 1)).andReturn();
        final MvcResult result = mockMvc.perform(delete("/users/{user_id}", 1)).andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Usuario 1 eliminado");
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(userRepository.findAll()).isEmpty();
        assertThat(loanRepository.findAll()).isEmpty();
    }



}
