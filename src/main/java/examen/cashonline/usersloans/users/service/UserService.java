package examen.cashonline.usersloans.users.service;

import examen.cashonline.usersloans.loans.entity.Loan;
import examen.cashonline.usersloans.loans.repository.LoanRepository;
import examen.cashonline.usersloans.users.repository.UserRepository;
import examen.cashonline.usersloans.users.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanRepository loanRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getOneUser(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Boolean deleteUser(Long id) {
        final Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            final List<Loan> loansbyUser = loanRepository.findByUser(optionalUser.get());
            loanRepository.deleteAll(loansbyUser);
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }
}
