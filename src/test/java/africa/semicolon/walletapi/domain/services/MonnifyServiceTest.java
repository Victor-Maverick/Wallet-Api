package africa.semicolon.walletapi.domain.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MonnifyServiceTest {

    @Autowired
    private MonnifyService monnifyService;

    @Test
    public void generateAccessToken() {
        monnifyService.generateAccessToken();

    }
}