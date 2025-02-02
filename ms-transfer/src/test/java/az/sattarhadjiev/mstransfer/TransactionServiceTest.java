package az.sattarhadjiev.mstransfer;

import static az.sattarhadjiev.mstransfer.enums.TransactionType.REFUND;
import static az.sattarhadjiev.mstransfer.enums.TransactionType.TOPUP;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import az.sattarhadjiev.mstransfer.model.Transaction;
import az.sattarhadjiev.mstransfer.repository.TransactionRepository;
import az.sattarhadjiev.mstransfer.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.*;

import java.time.LocalDateTime;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction parentTransaction;
    private Transaction childTransaction;
    private BigDecimal amount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        parentTransaction = Transaction.builder()
                .id(1L)
                .userId(101L)
                .type(TOPUP)
                .amount(BigDecimal.valueOf(1000.0))
                .parentTransaction(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        childTransaction = Transaction.builder()
                .id(2L)
                .userId(101L)
                .type(REFUND)
                .amount(BigDecimal.valueOf(200.0))
                .parentTransaction(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        amount = BigDecimal.valueOf(300.0);
    }

    @Test
    void save_shouldSaveTransactionSuccessfully() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(parentTransaction);

        transactionService.save(BigDecimal.valueOf(1000.0), TOPUP, 101L, null);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void validateRefund_shouldThrowIllegalArgumentException_whenRefundExceedsAmount() {
        BigDecimal parentAmount = new BigDecimal("100.00");
        parentTransaction.setAmount(parentAmount);

        BigDecimal childAmount = new BigDecimal("60.00");
        childTransaction.setAmount(childAmount);

        BigDecimal refundAmount = new BigDecimal("50.00");

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(parentTransaction));
        when(transactionRepository.getAllByParentTransaction(1L))
                .thenReturn(Collections.singletonList(childTransaction));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                transactionService.validateRefund(1L, refundAmount));

        assertEquals("Total refund amount exceeds the original transaction amount.", exception.getMessage());
    }


    @Test
    void validateRefundPermission_shouldThrowRuntimeException_whenUserIdDoesNotMatch() {
        parentTransaction = Transaction.builder()
                .id(1L)
                .userId(102L)
                .type(TOPUP)
                .amount(BigDecimal.valueOf(1000.0))
                .parentTransaction(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(parentTransaction));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transactionService.validateRefundPermission(1L, 101L);
        });
        assertEquals("Permission denied.", exception.getMessage());
    }

    @Test
    void findById_shouldReturnTransaction_whenTransactionExists() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(parentTransaction));

        Transaction result = transactionService.findById(1L);

        assertNotNull(result);
        assertEquals(parentTransaction, result);
    }

    @Test
    void findById_shouldThrowEntityNotFoundException_whenTransactionDoesNotExist() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            transactionService.findById(1L);
        });
        assertEquals("Transaction not found.", exception.getMessage());
    }

    @Test
    void findAllByParent_shouldReturnListOfTransactions() {
        List<Transaction> transactions = Arrays.asList(parentTransaction, childTransaction);
        when(transactionRepository.getAllByParentTransaction(1L)).thenReturn(transactions);

        List<Transaction> result = transactionService.findAllByParent(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(parentTransaction));
        assertTrue(result.contains(childTransaction));
    }
}
