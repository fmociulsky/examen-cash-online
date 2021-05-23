package examen.cashonline.usersloans.repository;

import examen.cashonline.usersloans.entity.Loan;
import examen.cashonline.usersloans.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    Page<Loan> findByUser(User user, Pageable pageable);
}
