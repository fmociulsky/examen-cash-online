package examen.cashonline.usersloans.loans.controller;

import com.sun.istack.NotNull;
import examen.cashonline.usersloans.loans.LoanResponseData;
import examen.cashonline.usersloans.loans.service.LoanService;
import examen.cashonline.usersloans.loans.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @RequestMapping("")
    public ResponseEntity<?> getLoans(@RequestParam(value = "user_id") @Nullable Long user_id,
                                      @RequestParam(value = "page") @NotNull int page,
                                      @RequestParam(value = "size") @NotNull int size ){
        final LoanResponseData loans = loanService.getLoans(user_id, page, size);
        if(loans.getItems().isEmpty()) return new ResponseEntity<>("Prestamos no encontrados", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<String> saveLoan(@RequestBody Loan loan) {
        final Loan savedLoan = loanService.saveLoan(loan);
        return new ResponseEntity<String>(String.format("Prestamo con Id %d creado", savedLoan.getId()), HttpStatus.CREATED);
    }

    @PutMapping("/{loan_id}/user/{user_id}")
    ResponseEntity<String> addLoanToUser(
            @PathVariable Long loan_id,
            @PathVariable Long user_id
    ) {
        final Boolean assignLoanToUser = loanService.assignLoanToUser(loan_id, user_id);
        if (assignLoanToUser){
            return new ResponseEntity<String>(String.format("Prestamo %d asignado al usuario %d", loan_id, user_id), HttpStatus.OK);
        }else
            return new ResponseEntity<String>(String.format("Usuario %d o Prestamo %d no encontrado", user_id, loan_id), HttpStatus.NOT_FOUND);
    }


}
