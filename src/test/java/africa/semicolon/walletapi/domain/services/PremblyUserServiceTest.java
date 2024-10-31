package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        VerificationRequest request = new VerificationRequest();
        request.setNin("18301174372");
        request.setImageUrl("C:\\Users\\DELL\\IdeaProjects\\WalletApi\\src\\main\\resources\\static\\first_image.jpeg");
        String response = premblyUserService.verifyNinAndFace(request);
        log.info(response);
        assertThat(response).isNotNull();
    }
}