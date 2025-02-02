package az.sattarhadjiev.mstransfer.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransferResponseDto {
    BigDecimal amount;
    BigDecimal newBalance;
    String message;

    public static TransferResponseDto topupInstance(BigDecimal balance, BigDecimal amount) {
        return TransferResponseDto.builder()
                .message("Topped up successfully!")
                .amount(amount)
                .newBalance(balance)
                .build();
    }

    public static TransferResponseDto purchaseInstance(BigDecimal balance, BigDecimal amount) {
        return TransferResponseDto.builder()
                .message("Purchased successfully!")
                .amount(amount)
                .newBalance(balance)
                .build();
    }

    public static TransferResponseDto refundInstance(BigDecimal balance, BigDecimal amount) {
        return TransferResponseDto.builder()
                .message("Refunded successfully!")
                .amount(amount)
                .newBalance(balance)
                .build();
    }
}
