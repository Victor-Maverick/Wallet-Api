package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.exception.WalletApiException;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static africa.semicolon.walletapi.domain.constants.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class KeycloakAdapterTest {
    private User user;
    @Autowired
    private KeycloakAdapter keycloakAdapter;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setFirstName("mson");
        user.setLastName("vic");
        user.setEmail("victormsonter@gmail.com");
        user.setPassword("Password11$");
        user.setRole(USER);
        keycloakAdapter.registerUserRepresentation(user);
    }
    @AfterEach
    public void tearDown(){
        keycloakAdapter.deleteUser(user.getEmail());
    }


    @Test
    public void loginTest_ShouldReturnLoginResponse_WhenCredentialsAreCorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victormsonter@gmail.com");
        loginRequest.setPassword("Password11$");
        LoginResponse response = keycloakAdapter.login(loginRequest);
        assertThat(response).isNotNull();
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsInvalid() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalidUser@example.com");
        loginRequest.setPassword("Password!123");
        assertThrows(WalletApiException.class, () -> keycloakAdapter.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsIncorrect() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victormsonter@gmail.com");
        loginRequest.setPassword("wrongPassword");

        assertThrows(WalletApiException.class, () -> keycloakAdapter.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenUsernameIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("");
        loginRequest.setPassword("Password!123");

        assertThrows(WalletApiException.class, () -> keycloakAdapter.login(loginRequest));
    }

    @Test
    public void loginTest_ShouldThrowException_WhenPasswordIsEmpty() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("victormsonter@gmail.com");
        loginRequest.setPassword("");

        assertThrows(WalletApiException.class, () -> keycloakAdapter.login(loginRequest));
    }



}
