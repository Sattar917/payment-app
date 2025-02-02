package az.sattarhadjiev.msauth.controller;

import az.sattarhadjiev.msauth.dto.request.AuthRequestDto;
import az.sattarhadjiev.msauth.dto.request.RegisterRequestDto;
import az.sattarhadjiev.msauth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PRIVATE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto user) {
        return new ResponseEntity<>(authService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody AuthRequestDto authRq) {
        return ResponseEntity.ok(authService.authenticate(authRq));
    }
}
