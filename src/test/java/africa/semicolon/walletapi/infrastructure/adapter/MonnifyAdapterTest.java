package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.domain.dtos.request.InitiateMonnifyTransactionRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitiateMonnifyTransactionResponse;
import africa.semicolon.walletapi.domain.dtos.response.MonnifyAuthenticateResponse;
import africa.semicolon.walletapi.domain.services.MonnifyService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MonnifyAdapterTest {
    private static final Logger log = LoggerFactory.getLogger(MonnifyAdapterTest.class);
    @Autowired
    private MonnifyService monnifyService;



    @Test
    public void generateAccessToken() {
        MonnifyAuthenticateResponse response = buildAccessToken();
        assertThat(response.getResponseBody().getAccessToken()).isNotNull();
        assertThat(response.getResponseMessage()).isEqualTo("success");
    }

    private MonnifyAuthenticateResponse buildAccessToken() {
        return monnifyService.generateAccessToken();
    }

    @Test
    public void initializeDepositTest(){
        InitiateMonnifyTransactionResponse response1 = initiateTransaction();
        assertThat(response1.getResponseMessage()).isEqualTo("success");
    }

    private InitiateMonnifyTransactionResponse initiateTransaction() {
        InitiateMonnifyTransactionRequest request = new InitiateMonnifyTransactionRequest();
        request.setEmail("asa@gmail.com");
        request.setAmount(500);
        request.setPaymentDescription("survival funds");
        return monnifyService.initializeMonnifyTransaction(request);
    }

}