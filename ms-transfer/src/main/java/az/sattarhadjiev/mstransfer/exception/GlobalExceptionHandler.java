package az.sattarhadjiev.mstransfer.exception;

import az.sattarhadjiev.mstransfer.dto.response.ExceptionResponse;
import az.sattarhadjiev.mstransfer.exception.custom.NotEnoughBalanceException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotEnoughBalanceException.class)
    public final ResponseEntity<ExceptionResponse> handleNotEnoughBalanceEx(NotEnoughBalanceException ex) {

        return ResponseEntity.badRequest().body(ExceptionResponse.newInstance(ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundEx(EntityNotFoundException ex) {

        return ResponseEntity.status(NOT_FOUND).body(ExceptionResponse.newInstance(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleUnknownEx(Exception ex) {
        log.trace(ex.getMessage(), ex);
        return new ResponseEntity<>(ExceptionResponse.newInstance(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }

}
