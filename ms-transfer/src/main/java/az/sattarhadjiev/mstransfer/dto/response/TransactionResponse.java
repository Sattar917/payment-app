package az.sattarhadjiev.mstransfer.dto.response;

import az.sattarhadjiev.mstransfer.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class TransactionResponse {

    Long id;

    Long userId;

    TransactionType type;

    BigDecimal amount;

    Long parentTransaction;

}
