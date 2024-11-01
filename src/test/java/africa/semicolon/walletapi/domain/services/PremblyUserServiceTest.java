package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import africa.semicolon.walletapi.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PremblyUserServiceTest {
    @Autowired
    private PremblyUserService premblyUserService;

    @Test
    public void verifyNinAndFaceTest() throws IOException {
        MockMultipartFile image = buildImageRequest();
        VerificationRequest request = new VerificationRequest();
        request.setNin("18301174372");
        request.setEmail("asa@gmail.com");
        request.setImage(image);
        String response = premblyUserService.verifyNinAndFace(request);
        log.info(response);
        assertThat(response).isNotNull();
    }

    @Test
    public void verifyForNonExistingEmailTest() throws IOException {
        MockMultipartFile image = buildImageRequest();
        VerificationRequest request = new VerificationRequest();
        request.setEmail("wrongemail@gmail.com");
        request.setImage(image);
        request.setNin("18301174372");
        assertThrows(PiggyWalletException.class, () -> premblyUserService.verifyNinAndFace(request));
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