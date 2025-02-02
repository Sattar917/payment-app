package az.sattarhadjiev.mstransfer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class CreateAccountRequest {

    @NotBlank
    String name;

    @NotBlank
    String surname;

    BigDecimal balance;

    @NotBlank
    String phoneNumber;

    LocalDateTime birthDate;
}
