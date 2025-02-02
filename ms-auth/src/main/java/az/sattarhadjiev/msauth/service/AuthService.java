package az.sattarhadjiev.msauth.service;

import az.sattarhadjiev.msauth.dto.request.AuthRequestDto;
import az.sattarhadjiev.msauth.dto.request.RegisterRequestDto;
import az.sattarhadjiev.msauth.mapper.UserMapper;
import az.sattarhadjiev.msauth.model.User;
import az.sattarhadjiev.msauth.repository.UserRepository;
import az.sattarhadjiev.msauth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static az.sattarhadjiev.msauth.enums.BusinessException.USERNAME_NOT_FOUND;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AuthService {

    JwtUtil jwtUtil;

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;

    UserMapper userMapper;

    public String authenticate(AuthRequestDto authRq) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRq.getUsername(), authRq.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            Long userId = getUsernameFromDatabase(userDetails.getUsername());

            return generateToken(userId, authRq.getUsername());
        }

        throw new RuntimeException();
    }

    @Transactional
    public String save(RegisterRequestDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(userMapper.map(user));

        return savedUser.getUsername();
    }

    private Long getUsernameFromDatabase(String gsmNumber) {
        return userRepository.findByUsername(gsmNumber)
                .map(User::getId)
                .orElseThrow(() -> new UsernameNotFoundException(USERNAME_NOT_FOUND.getMsg()));
    }

    public String generateToken(Long userId, String username) {
        return jwtUtil.generateToken(userId, username);
    }
}
