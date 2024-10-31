package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.exception.InvalidPasswordException;
import africa.semicolon.walletapi.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    public void loginTest(){
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setUsername("mesh@gmail.com");
    loginRequest.setPassword("Password!123");
    LoginResponse response = authService.login(loginRequest);
    assertThat(response).isNotNull();
    }

    @Test
    public void loginTest_ShouldReturnLoginResponse_WhenCredentialsAreCorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mesh@gmail.com");
        loginRequest.setPassword("Password!123");
        LoginResponse response = authService.login(loginRequest);
        assertThat(response).isNotNull();
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsInvalid() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalidUser@example.com");
        loginRequest.setPassword("Password!123");

        assertThrows(UserNotFoundException.class, () -> authService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsIncorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mesh@gmail.com");
        loginRequest.setPassword("wrongPassword");

        assertThrows(InvalidPasswordException.class, () -> authService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("Password!123");

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mesh@gmail.com");
        loginRequest.setPassword("");

        assertThrows(InvalidPasswordException.class, () -> authService.login(loginRequest));
    }



}
