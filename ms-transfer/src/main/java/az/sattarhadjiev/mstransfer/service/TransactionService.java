package az.sattarhadjiev.mstransfer.service;

import az.sattarhadjiev.mstransfer.enums.TransactionType;
import az.sattarhadjiev.mstransfer.model.Transaction;
import az.sattarhadjiev.mstransfer.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static az.sattarhadjiev.mstransfer.enums.BusinessException.*;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository transactionRepository;

    @Transactional
    public void save(BigDecimal amount, TransactionType type, Long userId, Long parent) {
        Transaction transaction = Transaction.builder()
                .type(type)
                .amount(amount)
                .userId(userId)
                .parentTransaction(parent)
                .build();

        transactionRepository.save(transaction);
    }

    public void validateRefund(Long transactionId, BigDecimal amount) {
        List<Transaction> transactions = findAllByParent(transactionId);
        Transaction parentTransaction = findById(transactionId);

        BigDecimal totalRefund = transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(amount);

        if (totalRefund.compareTo(parentTransaction.getAmount()) > 0) {
            throw new IllegalArgumentException(ALREADY_REFUNDED.getMsg());
        }
    }

    public void validateRefundPermission(Long transactionId, Long userId) {
        Transaction parentTransaction = findById(transactionId);

        if (!parentTransaction.getUserId().equals(userId)) {
            throw new RuntimeException(PERMISSION_DENIED.getMsg());
        }
    }

    public List<Transaction> findAllByParent(Long parentId) {
        return transactionRepository.getAllByParentTransaction(parentId);
    }

    public Transaction findById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .orElseThrow(() -> new EntityNotFoundException(TRANSACTION_NOT_FOUND.getMsg()));
    }
}
