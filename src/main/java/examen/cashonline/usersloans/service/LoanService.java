package examen.cashonline.usersloans.service;

import examen.cashonline.usersloans.entity.Loan;
import examen.cashonline.usersloans.entity.User;
import examen.cashonline.usersloans.model.LoanResponseData;
import examen.cashonline.usersloans.model.PageDetail;
import examen.cashonline.usersloans.repository.LoanRepository;
import examen.cashonline.usersloans.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    public List<Loan> saveLoans(List<Loan> loans){
        return loanRepository.saveAll(loans);
    }

    public Optional<Loan> getLoan(int id) {
        return loanRepository.findById(id);
    }

    public boolean deleteLoan(int id) {
        final Optional<Loan> optionalUser = loanRepository.findById(id);
        if(optionalUser.isPresent()) {
            loanRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }

    public Loan saveLoan(Loan loan, int user_id) {
        final Optional<User> user = userRepository.findById(user_id);
        loan.setUser(user.get());
        return loanRepository.save(loan);
    }

    public LoanResponseData getLoans(Integer user_id, int page, int size) {
        final Optional<User> user = userRepository.findById(user_id);
        final Page<Loan> loanPage = loanRepository.findByUser(user.get(), PageRequest.of(page, size));
        final PageDetail pageDetail = new PageDetail(page, size);
        final List<Loan> loanList = loanPage.get().collect(Collectors.toList());
        return new LoanResponseData(loanList, pageDetail);
    }
}
