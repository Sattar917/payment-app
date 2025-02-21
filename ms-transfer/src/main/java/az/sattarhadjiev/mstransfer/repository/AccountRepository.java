package az.sattarhadjiev.mstransfer.repository;

import az.sattarhadjiev.mstransfer.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
