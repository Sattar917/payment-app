import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import az.sattarhadjiev.mstransfer.dto.request.CreateAccountRequest;
import az.sattarhadjiev.mstransfer.dto.response.AccountResponse;
import az.sattarhadjiev.mstransfer.mapper.AccountMapper;
import az.sattarhadjiev.mstransfer.model.Account;
import az.sattarhadjiev.mstransfer.repository.AccountRepository;
import az.sattarhadjiev.mstransfer.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountService accountService;

    private CreateAccountRequest createAccountRequest;

    private Account account;

    private AccountResponse accountResponse;

    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        now = LocalDateTime.now();

        createAccountRequest = new CreateAccountRequest(
                "John", "Doe",  BigDecimal.valueOf(1000.0), "1234567890", LocalDateTime.parse("1990-01-01T00:00:00")
        );
        account = new Account(1L, 101L, "John", "Doe", BigDecimal.valueOf(1000.0), "1234567890",
                LocalDateTime.parse("1990-01-01T00:00:00"), now, now);
        accountResponse = new AccountResponse(1L, "John", "Doe", BigDecimal.valueOf(1000.0),
                "1234567890", LocalDateTime.parse("1990-01-01T00:00:00"));
    }

    @Test
    void saveAccount_shouldSaveAccountSuccessfully() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.saveAccount(createAccountRequest, 1L);

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void getAllAccounts_shouldReturnListOfAccountResponses() {
        List<Account> accounts = Collections.singletonList(account);
        List<AccountResponse> accountResponses = accounts.stream()
                .map(a -> new AccountResponse(a.getId(), a.getName(), a.getSurname(), a.getBalance(), a.getPhoneNumber(), a.getBirthDate()))
                .collect(Collectors.toList());
        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.toDtoList(accounts)).thenReturn(accountResponses);

        List<AccountResponse> result = accountService.getAllAccounts();

        assertEquals(1, result.size());
        assertEquals(accountResponse, result.get(0));
    }
}
