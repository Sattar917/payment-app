package az.sattarhadjiev.mstransfer.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor
@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum BusinessException {

    PERMISSION_DENIED("Permission denied."),
    ACCOUNT_NOT_FOUND("Account not found."),
    ACCOUNT_ALREADY_EXISTS("An account with this userId already exists."),
    TRANSACTION_NOT_FOUND("Transaction not found."),
    NOT_ENOUGH_BALANCE("Not enough balance! Available balance: %s"),
    ALREADY_REFUNDED("Total refund amount exceeds the original transaction amount.");

    String msg;

}
