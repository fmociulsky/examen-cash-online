package examen.cashonline.usersloans.controller;

import com.sun.istack.NotNull;
import examen.cashonline.usersloans.entity.Loan;
import examen.cashonline.usersloans.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @RequestMapping("/{id}")
    public ResponseEntity<?> getLoan(@PathVariable(value = "id") int id){
        final Optional<Loan> loan = loanService.getLoan(id);
        if(!loan.isPresent()) return new ResponseEntity<String>(format("Prestamo con Id %d no encontrado", id), HttpStatus.NOT_FOUND);
        return new ResponseEntity<Loan>(loan.get(), HttpStatus.OK);
    }

    @RequestMapping("")
    public ResponseEntity<?> getLoans(@RequestParam(value = "user_id") @Nullable Integer user_id,
                                      @RequestParam(value = "page") @NotNull int page,
                                      @RequestParam(value = "size") @NotNull int size ){
        return new ResponseEntity<>(loanService.getLoans(user_id, page, size), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable(value = "id") int id){
        if(loanService.deleteLoan(id)) return new ResponseEntity<String>(format("Prestamo %d eliminado", id), HttpStatus.OK);
        else return new ResponseEntity<String>(format("Prestamo %d no encontrado", id), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{user_id}")
    public ResponseEntity<String> saveUser(@RequestBody Loan loan, @PathVariable(name = "user_id") int user_id){
        final Loan savedLoan = loanService.saveLoan(loan, user_id);
        return new ResponseEntity<String>(format("Prestamo con Id %d creado", savedLoan.getId()), HttpStatus.CREATED);
    }

}
