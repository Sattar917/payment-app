package az.sattarhadjiev.mstransfer.service;


import az.sattarhadjiev.mstransfer.dto.request.PurchaseRequestDto;
import az.sattarhadjiev.mstransfer.dto.request.RefundRequestDto;
import az.sattarhadjiev.mstransfer.dto.request.TopupRequestDto;
import az.sattarhadjiev.mstransfer.dto.response.TransferResponseDto;
import az.sattarhadjiev.mstransfer.exception.custom.NotEnoughBalanceException;
import az.sattarhadjiev.mstransfer.model.Account;
import az.sattarhadjiev.mstransfer.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static az.sattarhadjiev.mstransfer.enums.BusinessException.ACCOUNT_NOT_FOUND;
import static az.sattarhadjiev.mstransfer.enums.BusinessException.NOT_ENOUGH_BALANCE;
import static az.sattarhadjiev.mstransfer.enums.TransactionType.*;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransferService {

    AccountRepository accountRepository;

    TransactionService transactionService;

    @Transactional
    public TransferResponseDto topUp(Long userId, TopupRequestDto topupRq) {
        Account account = getAccount(userId);

        account.setBalance(account.getBalance().add(topupRq.getAmount()));

        transactionService.save(topupRq.getAmount(), TOPUP, userId, null);

        return TransferResponseDto.topupInstance(account.getBalance(), topupRq.getAmount());
    }

    @Transactional
    public TransferResponseDto purchase(Long userId, PurchaseRequestDto purchaseRq) {
        Account account = getAccount(userId);

        validateBalance(account, purchaseRq.getAmount());

        account.setBalance(account.getBalance().subtract(purchaseRq.getAmount()));

        transactionService.save(purchaseRq.getAmount(), PURCHASE, userId, null);

        return TransferResponseDto.purchaseInstance(account.getBalance(), purchaseRq.getAmount());
    }


    @Transactional
    public TransferResponseDto refund(Long userId, RefundRequestDto refundRq) {
        transactionService.validateRefundPermission(refundRq.getTransactionId(), userId);
        transactionService.validateRefund(refundRq.getTransactionId(), refundRq.getAmount());

        Account account = getAccount(userId);
        account.setBalance(account.getBalance().add(refundRq.getAmount()));
        accountRepository.save(account);

        transactionService.save(refundRq.getAmount(), REFUND, userId, refundRq.getTransactionId());

        return TransferResponseDto.refundInstance(account.getBalance(), refundRq.getAmount());
    }

    private void validateBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new NotEnoughBalanceException(format(NOT_ENOUGH_BALANCE.getMsg(), account.getBalance()));
        }
    }

    private Account getAccount(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(ACCOUNT_NOT_FOUND.getMsg()));
    }
}
