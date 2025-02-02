package az.sattarhadjiev.mstransfer.controller;

import az.sattarhadjiev.mstransfer.dto.request.CreateAccountRequest;
import az.sattarhadjiev.mstransfer.dto.response.AccountResponse;
import az.sattarhadjiev.mstransfer.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping
    public void createAccount(@Valid @RequestBody CreateAccountRequest request) {
        accountService.saveAccount(request);
    }


}
