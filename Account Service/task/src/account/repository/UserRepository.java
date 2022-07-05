package account.repository;

import account.model.Payment;
import account.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    User findTopByEmail(String email);
    List<Payment> findPaymentByEmail(String email);
}

