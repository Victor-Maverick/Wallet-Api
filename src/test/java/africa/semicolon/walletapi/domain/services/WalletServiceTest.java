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
        request.setAmount(300);
        InitializePaymentResponse response = walletService.initializeDeposit(request);
        Thread.sleep(20000);
        VerifyPaymentResponse response1 = walletService.deposit(response.getData().getReference(),501L);
        assertThat(response1.getData().getStatus()).isEqualTo("success");
    }


}