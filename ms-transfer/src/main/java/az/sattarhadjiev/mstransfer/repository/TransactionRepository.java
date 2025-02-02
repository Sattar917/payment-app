package az.sattarhadjiev.mstransfer.repository;

import az.sattarhadjiev.mstransfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
