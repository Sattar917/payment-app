package az.sattarhadjiev.mstransfer.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ExceptionResponse {

    String message;
    LocalDateTime dateTime;

    public static ExceptionResponse newInstance(String message) {
        return ExceptionResponse.builder()
                .message(message)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
