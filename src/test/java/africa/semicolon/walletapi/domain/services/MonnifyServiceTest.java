package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.response.MonnifyAuthenticateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MonnifyServiceTest {

    @Autowired
    private MonnifyService monnifyService;

    @Test
    public void generateAccessToken() {
        MonnifyAuthenticateResponse response = monnifyService.generateAccessToken();
        assertThat(response.getResponseBody().getAccessToken()).isNotNull();
        assertThat(response.getResponseMessage()).isEqualTo("success");
    }
}