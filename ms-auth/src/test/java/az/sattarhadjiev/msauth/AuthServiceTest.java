package az.sattarhadjiev.msauth;

import az.sattarhadjiev.msauth.dto.request.AuthRequestDto;
import az.sattarhadjiev.msauth.dto.request.RegisterRequestDto;
import az.sattarhadjiev.msauth.mapper.UserMapper;
import az.sattarhadjiev.msauth.model.User;
import az.sattarhadjiev.msauth.repository.UserRepository;
import az.sattarhadjiev.msauth.service.AuthService;
import az.sattarhadjiev.msauth.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    private AuthRequestDto authRequest;
    private RegisterRequestDto registerRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authRequest = new AuthRequestDto("user1", "password");
        registerRequest = new RegisterRequestDto("name", "surname","user2", "password");
    }

    @Test
    void authenticate_shouldReturnToken_whenValidCredentials() {
        String expectedToken = "valid-token";

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user1");
        when(userRepository.findByUsername("user1")).thenReturn(java.util.Optional.of(new User()));
        when(jwtUtil.generateToken(anyLong(), eq("user1"))).thenReturn(expectedToken);

        String token = authService.authenticate(authRequest);

        assertEquals(expectedToken, token);
    }


    @Test
    void authenticate_shouldThrowException_whenInvalidCredentials() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Authentication failed"));

        assertThrows(RuntimeException.class, () -> authService.authenticate(authRequest));
    }

    @Test
    void save_shouldReturnUsername_whenUserIsSuccessfullyRegistered() {
        User savedUser = new User();
        savedUser.setUsername("user2");
        when(userMapper.map(registerRequest)).thenReturn(savedUser);
        when(userRepository.save(savedUser)).thenReturn(savedUser);
        when(passwordEncoder.encode("password")).thenReturn("encoded-password");

        String username = authService.save(registerRequest);

        assertEquals("user2", username);
    }

    @Test
    void save_shouldThrowException_whenUsernameAlreadyExists() {
        when(userRepository.findByUsername("user2")).thenReturn(java.util.Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> authService.save(registerRequest));
    }

    @Test
    void getUsernameFromDatabase_shouldReturnUserId_whenUserExists() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findByUsername("user1")).thenReturn(java.util.Optional.of(user));

        Long userId = authService.getUsernameFromDatabase("user1");

        assertEquals(1L, userId);
    }

    @Test
    void getUsernameFromDatabase_shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findByUsername("user1")).thenReturn(java.util.Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.getUsernameFromDatabase("user1"));
    }

    @Test
    void generateToken_shouldReturnToken_whenValidInput() {
        when(jwtUtil.generateToken(1L, "user1")).thenReturn("generated-token");

        String token = authService.generateToken(1L, "user1");

        assertEquals("generated-token", token);
    }
}

