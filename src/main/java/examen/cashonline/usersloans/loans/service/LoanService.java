package examen.cashonline.usersloans.loans.service;

import examen.cashonline.usersloans.model.PageDetail;
import examen.cashonline.usersloans.loans.repository.LoanRepository;
import examen.cashonline.usersloans.loans.LoanResponseData;
import examen.cashonline.usersloans.loans.entity.Loan;
import examen.cashonline.usersloans.users.entity.User;
import examen.cashonline.usersloans.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public Loan saveLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    public Boolean assignLoanToUser(Long loan_id, Long user_id) {
        final Optional<User> optionalUser = userRepository.findById(user_id);
        final Optional<Loan> optionalLoan = loanRepository.findById(loan_id);
        if(optionalUser.isPresent() && optionalLoan.isPresent()){
            final Loan loan = optionalLoan.get();
            final User user = optionalUser.get();
            loan.setUser(user);
            loanRepository.save(loan);
            return true;
        }else
            return false;
    }

    public LoanResponseData getLoans(Long user_id, int page, int size) {
        final Page<Loan> loanPage;
        final PageDetail pageDetail = new PageDetail(page, size);
        final PageRequest pageRequest = PageRequest.of(page, size);

        if(user_id != null){
            final Optional<User> user = userRepository.findById(user_id);
            if(!user.isPresent()) return new LoanResponseData(new ArrayList<>(), pageDetail);
            loanPage = loanRepository.findByUser(user.get(), pageRequest);
        }else {
            loanPage = loanRepository.findAll(pageRequest);
        }

        final List<Loan> loanList = loanPage.get().collect(Collectors.toList());
        return new LoanResponseData(loanList, pageDetail);
    }
}
