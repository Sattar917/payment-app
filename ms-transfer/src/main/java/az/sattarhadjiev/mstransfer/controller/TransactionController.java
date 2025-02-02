package az.sattarhadjiev.mstransfer.controller;

import az.sattarhadjiev.mstransfer.dto.response.TransactionResponse;
import az.sattarhadjiev.mstransfer.model.Transaction;
import az.sattarhadjiev.mstransfer.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;

    @GetMapping
    public List<TransactionResponse> getTransactionsByUserId(@RequestHeader Long userId) {
        return transactionService.getTransactionsByUserId(userId);
    }

}
