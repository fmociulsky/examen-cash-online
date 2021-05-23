package examen.cashonline.usersloans.repository;

import examen.cashonline.usersloans.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
