package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
public class PremblyAdapterTest {
    private User user;
    @Autowired
    private PremblyAdapter premblyAdapter;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setFirstName("mson");
        user.setLastName("vic");
        user.setEmail("victormsonter@gmail.com");
        user.setPassword("Password11$");
        user.setRole(USER);
        user = userService.register(user);
    }
    @AfterEach
    public void tearDown(){
        userService.deleteUser(user.getEmail());
    }

    @Test
    public void verifyNinAndFaceTest() throws IOException {
        log.info("user : {}", user);
        MockMultipartFile image = buildImageRequest();
        VerificationRequest request = new VerificationRequest();
        request.setNin("18301174372");
        request.setEmail("victormsonter@gmail.com");
        request.setImage(image);
        String response = premblyAdapter.verifyNinAndFace(request);
        assertThat(response).isNotNull();
    }

    @Test
    public void verifyForNonExistingEmailTest() throws IOException {
        MockMultipartFile image = buildImageRequest();
        VerificationRequest request = new VerificationRequest();
        request.setEmail("wrongemail@gmail.com");
        request.setImage(image);
        request.setNin("18301174372");
        assertThrows(WalletApiException.class, () -> premblyAdapter.verifyNinAndFace(request));
    }

    private static @NotNull MockMultipartFile buildImageRequest() throws IOException {
        String filePath = "src/main/resources/static/first_image.jpeg";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        return new MockMultipartFile(
                "image",
                file.getName(),
                MediaType.IMAGE_JPEG_VALUE,
                fileInputStream
        );
    }

}
