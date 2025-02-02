package az.sattarhadjiev.msauth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static az.sattarhadjiev.msauth.enums.BusinessException.USER_ALREADY_EXISTS;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleUserAlreadyExistEx(DataIntegrityViolationException ex) {
        log.trace(ex.getMessage(), ex);
        return new ResponseEntity<>(USER_ALREADY_EXISTS.getMsg(), HttpStatus.CONFLICT);
    }
}
