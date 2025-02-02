package az.sattarhadjiev.msauth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessException {

    USERNAME_NOT_FOUND("Username not found."),
    USER_ALREADY_EXISTS("User with such phone number already exists.");

    private final String msg;
}
