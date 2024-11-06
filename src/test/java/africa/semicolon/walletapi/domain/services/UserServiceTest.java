package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.exception.InvalidPasswordApiException;
import africa.semicolon.walletapi.domain.exception.UserNameExistsApiException;
import africa.semicolon.walletapi.domain.exception.WalletApiException;
import africa.semicolon.walletapi.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static africa.semicolon.walletapi.domain.constants.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserOutputPort userOutputPort;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private User registeredUser;

    @BeforeEach
    public void setUp(){
        User user = new User();
        user.setFirstName("mson");
        user.setLastName("vic");
        user.setEmail("victormsonter@gmail.com");
        user.setPassword("Password11$");
        user.setRole(USER);
        user = userService.register(user);
    }
    @AfterEach
    public void deleteUser(){
        userService.deleteUser(registeredUser.getEmail());
    }
    @Test
    public void registerTest() {
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getEmail()).isEqualTo("saan@gmail.com");
    }

    @Test
    public void registerExistingEmailTest() {
        User user = new User();
                user.setFirstName("John");
                user.setLastName("Doe");
                user.setEmail("saan@gmail.com");
                user.setPassword("password");
        assertThrows(UserNameExistsApiException.class, ()-> userService.register(user));
    }

    @Test
    public void loginTest_ShouldReturnLoginResponse_WhenCredentialsAreCorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("saan@gmail.com");
        loginRequest.setPassword("Password11$");
        LoginResponse response = userService.login(loginRequest);
        assertThat(response).isNotNull();
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsInvalid() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalidUser@example.com");
        loginRequest.setPassword("Password!123");
        assertThrows(WalletApiException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsIncorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("saan@gmail.com");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordApiException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("Password!123");
        assertThrows(WalletApiException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("saan@gmail.com");
        loginRequest.setPassword("");
        assertThrows(InvalidPasswordApiException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void updateExistingUserTest_successful(){
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("newPass!111");
        userService.updateUser("saan@gmail.com", user);
        User existingUser = userOutputPort.getByEmail("saan@gmail.com");
        assertThat(existingUser.getFirstName()).isEqualTo("John");
    }





}