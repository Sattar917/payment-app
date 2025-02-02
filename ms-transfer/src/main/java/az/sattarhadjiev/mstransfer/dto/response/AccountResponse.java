package az.sattarhadjiev.mstransfer.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Value
@Builder
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class AccountResponse {

    Long id;

    String name;

    String surname;

    BigDecimal balance;

    String phoneNumber;

    LocalDateTime birthDate;
}