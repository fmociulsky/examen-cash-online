package examen.cashonline.usersloans.loans.repository;

import examen.cashonline.usersloans.loans.entity.Loan;
import examen.cashonline.usersloans.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser(User user);

    Page<Loan> findByUser(User user, Pageable pageable);
}
