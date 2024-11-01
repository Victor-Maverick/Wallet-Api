package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.InitializeTransferResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaystackServiceTest {
    private final PaystackService paystackService;


    public PaystackServiceTest() {
        this.paystackService = new PaystackService(new RestTemplate());
    }

    @Test
    public void initializePaymentWithPaystackTest() throws Exception {
        InitializePaymentRequest request = new InitializePaymentRequest();
        request.setEmail("victormsonter@gmail.com");
        request.setAmount(100.00);
        InitializePaymentResponse response = paystackService.initializePayment(request);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(true);
        System.out.println(response.getData().getReference());
    }

    @Test
    public void verifyPaymentWithPaystackTest() throws Exception {
        InitializePaymentRequest request = new InitializePaymentRequest();
        request.setEmail("victormsonter@gmail.com");
        request.setAmount(100.00);
        InitializePaymentResponse response1 = paystackService.initializePayment(request);
        VerifyPaymentResponse response = paystackService.Verification(response1.getData().getReference());
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
    }
    @Test
    public void testInitializeTransfer(){
        InitializeTransferRequest request = new InitializeTransferRequest();
        request.setReason("test reason");
        request.setAmount(100.00);
        request.setSenderWalletId(52L);
        request.setReceiverWalletId(102L);
        InitializeTransferResponse response = paystackService.initializeTransfer(request);
        assertThat(response).isNotNull();
    }


}