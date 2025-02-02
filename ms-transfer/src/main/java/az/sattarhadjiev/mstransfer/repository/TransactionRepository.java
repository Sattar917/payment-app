package az.sattarhadjiev.mstransfer.repository;

import az.sattarhadjiev.mstransfer.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> getAllByParentTransaction(Long id);

}
