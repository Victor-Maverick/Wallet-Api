package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.exception.InvalidPasswordException;
import africa.semicolon.walletapi.domain.exception.UserNameExistsException;
import africa.semicolon.walletapi.domain.exception.UserNotFoundException;
import africa.semicolon.walletapi.domain.model.User;
import lombok.extern.slf4j.Slf4j;
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

    @Test
    public void registerTest() {
        User user = User.builder()
                .firstName("kriz")
                .lastName("akaa")
                .email("saan@gmail.com")
                .password("Password11$")
                .role(USER)
                .build();
        User registeredUser = userService.register(user);
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void registerExistingEmailTest() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("saan@gmail.com")
                .password("password")
                .build();
        assertThrows(UserNameExistsException.class, ()-> userService.register(user));
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
        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsIncorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("saan@gmail.com");
        loginRequest.setPassword("wrongPassword");
        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("Password!123");
        assertThrows(UserNotFoundException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("mesh@gmail.com");
        loginRequest.setPassword("");
        assertThrows(InvalidPasswordException.class, () -> userService.login(loginRequest));
    }

    @Test
    public void updateExistingUserTest_successful(){
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("newPass!111");
        userService.updateUser("saan@gmail.com", user);
        User existingUser = userOutputPort.getByEmail("asa@gmail.com");
        assertThat(existingUser.getFirstName()).isEqualTo("John");
    }





}