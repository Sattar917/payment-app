package az.sattarhadjiev.mstransfer.service;

import az.sattarhadjiev.mstransfer.dto.AccountResponse;
import az.sattarhadjiev.mstransfer.dto.CreateAccountRequest;
import az.sattarhadjiev.mstransfer.mapper.AccountMapper;
import az.sattarhadjiev.mstransfer.model.Account;
import az.sattarhadjiev.mstransfer.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountService {

    AccountRepository accountRepository;

    AccountMapper accountMapper;

    @Transactional
    public void saveAccount(CreateAccountRequest createAccountRq) {
        Account account = Account.builder()
                .name(createAccountRq.getName())
                .surname(createAccountRq.getSurname())
                .balance(createAccountRq.getBalance())
                .phoneNumber(createAccountRq.getPhoneNumber())
                .birthDate(createAccountRq.getBirthDate())
                .build();

        accountRepository.save(account);
    }

    public List<AccountResponse> getAllAccounts() {
        return accountMapper.toDtoList(accountRepository.findAll());
    }

}
