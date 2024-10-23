package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.request.TransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletServiceTest {
    @Autowired
    private WalletService walletService;

    @Test
    public void depositTest() throws Exception {
        DepositRequest request = new DepositRequest();
        request.setEmail("johndoe@gmail.com");
        request.setWalletId(501L);
        request.setAmount(new BigDecimal(300));
        InitializePaymentResponse response = walletService.initializeDeposit(request);
        Thread.sleep(20000);
        VerifyPaymentResponse response1 = walletService.deposit(response.getData().getReference(),501L);
        assertThat(response1.getData().getStatus()).isEqualTo("success");
    }

    @Test
    public void transferTest() throws Exception {
        InitializeTransferRequest request = new InitializeTransferRequest();
        request.setEmail("johndoe@gmail.com");
        request.setAmount(new BigDecimal(600));
        InitializePaymentResponse response = walletService.initializeTransfer(request);
        Thread.sleep(20000);
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSenderWalletId(501L);
        transferRequest.setReceiverWalletId(500L);
        transferRequest.setPin("1111");
        VerifyPaymentResponse verifyResponse = walletService.transfer(response.getData().getReference(), transferRequest);
        assertThat(verifyResponse.getData().getStatus()).isEqualTo("success");
    }
}