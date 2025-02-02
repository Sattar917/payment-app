package az.sattarhadjiev.mstransfer;

import static az.sattarhadjiev.mstransfer.enums.TransactionType.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import az.sattarhadjiev.mstransfer.dto.request.PurchaseRequestDto;
import az.sattarhadjiev.mstransfer.dto.request.RefundRequestDto;
import az.sattarhadjiev.mstransfer.dto.request.TopupRequestDto;
import az.sattarhadjiev.mstransfer.dto.response.TransferResponseDto;
import az.sattarhadjiev.mstransfer.exception.custom.NotEnoughBalanceException;
import az.sattarhadjiev.mstransfer.model.Account;
import az.sattarhadjiev.mstransfer.repository.AccountRepository;
import az.sattarhadjiev.mstransfer.service.TransactionService;
import az.sattarhadjiev.mstransfer.service.TransferService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.util.Optional;

class TransferServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransferService transferService;

    private Account account;

    private TopupRequestDto topupRequestDto;

    private PurchaseRequestDto purchaseRequestDto;

    private RefundRequestDto refundRequestDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        account = new Account(1L, 101L, "John", "Doe", BigDecimal.valueOf(1000.0), "1234567890", null, null, null);

        topupRequestDto = new TopupRequestDto(BigDecimal.valueOf(500.0));
        purchaseRequestDto = new PurchaseRequestDto(BigDecimal.valueOf(200.0));
        refundRequestDto = new RefundRequestDto( 1L, BigDecimal.valueOf(100.0));
    }

    @Test
    void topUp_shouldIncreaseBalanceAndSaveTransaction() {
        when(accountRepository.findByUserId(101L)).thenReturn(Optional.of(account));

        TransferResponseDto response = transferService.topUp(101L, topupRequestDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(1500.0), account.getBalance());
        verify(transactionService, times(1)).save(topupRequestDto.getAmount(), TOPUP, 101L, null);
    }

    @Test
    void purchase_shouldDecreaseBalanceAndSaveTransaction_whenBalanceIsSufficient() {

        when(accountRepository.findByUserId(101L)).thenReturn(Optional.of(account));

        TransferResponseDto response = transferService.purchase(101L, purchaseRequestDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(800.0), account.getBalance());
        verify(transactionService, times(1)).save(purchaseRequestDto.getAmount(), PURCHASE, 101L, null);
    }

    @Test
    void refund_shouldProcessRefundAndSaveTransaction() {
        when(accountRepository.findByUserId(101L)).thenReturn(Optional.of(account));

        doNothing().when(transactionService).validateRefundPermission(1L, 101L);
        doNothing().when(transactionService).validateRefund(1L, refundRequestDto.getAmount());


        TransferResponseDto response = transferService.refund(101L, refundRequestDto);

        assertNotNull(response);
        assertEquals(BigDecimal.valueOf(1100.0), account.getBalance());
        verify(transactionService, times(1)).save(refundRequestDto.getAmount(), REFUND, 101L, 1L);
    }

    @Test
    void getAccount_shouldThrowEntityNotFoundException_whenAccountDoesNotExist() {
        when(accountRepository.findByUserId(101L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> transferService.topUp(101L, topupRequestDto));
        assertEquals("Account not found.", exception.getMessage());
    }
}

