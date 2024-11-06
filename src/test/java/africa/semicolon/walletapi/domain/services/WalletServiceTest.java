package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.request.TransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.WalletResponse;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static africa.semicolon.walletapi.domain.constants.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WalletServiceTest {
    @Autowired
    private WalletService walletService;
    private User user;

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
    public void depositTest() throws Exception {
        DepositRequest request = new DepositRequest();
        request.setEmail("victormsonter@gmail.com");
        WalletResponse wallet = userService.getUser("victormsonter@gmail.com").getWalletResponse();
        request.setWalletId(wallet.getWalletId());
        request.setAmount(300);
        InitializePaymentResponse response = walletService.initializeDeposit(request);
        Thread.sleep(30000);
        VerifyPaymentResponse response1 = walletService.deposit(response.getData().getReference(),wallet.getWalletId());
        assertThat(response1.getData().getStatus()).isEqualTo("success");
    }


}