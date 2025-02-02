package az.sattarhadjiev.mstransfer.service;

import az.sattarhadjiev.mstransfer.dto.response.AccountResponse;
import az.sattarhadjiev.mstransfer.dto.request.CreateAccountRequest;
import az.sattarhadjiev.mstransfer.mapper.AccountMapper;
import az.sattarhadjiev.mstransfer.model.Account;
import az.sattarhadjiev.mstransfer.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static az.sattarhadjiev.mstransfer.enums.BusinessException.ACCOUNT_ALREADY_EXISTS;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

    AccountMapper accountMapper;

    @Transactional
    public void saveAccount(CreateAccountRequest createAccountRq, Long userId) {
        if (accountRepository.existsByUserId(userId)) {
            throw new IllegalStateException(ACCOUNT_ALREADY_EXISTS.getMsg());
        }

        Account account = Account.builder()
                .name(createAccountRq.getName())
                .surname(createAccountRq.getSurname())
                .balance(createAccountRq.getBalance())
                .phoneNumber(createAccountRq.getPhoneNumber())
                .birthDate(createAccountRq.getBirthDate())
                .userId(userId)
                .build();

        accountRepository.save(account);
    }

    public List<AccountResponse> getAllAccounts() {
        return accountMapper.toDtoList(accountRepository.findAll());
    }

}
