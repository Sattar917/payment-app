package az.sattarhadjiev.msauth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Past
    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String password;

    @NotBlank
    private String username;
}
