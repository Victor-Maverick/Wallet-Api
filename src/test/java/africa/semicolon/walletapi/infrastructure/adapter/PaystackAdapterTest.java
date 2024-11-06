package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semicolon.walletapi.domain.constants.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PaystackAdapterTest {
    @Autowired
    private  PaystackAdapter paystackAdapter;
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
    public void initializePaymentWithPaystackTest() throws Exception {
        InitializePaymentRequest request = new InitializePaymentRequest();
        request.setEmail("victormsonter@gmail.com");
        request.setAmount(100.00);
        InitializePaymentResponse response = paystackAdapter.initializePayment(request);
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(true);
        System.out.println(response.getData().getReference());
    }

    @Test
    public void verifyPaymentWithPaystackTest() throws Exception {
        InitializePaymentRequest request = new InitializePaymentRequest();
        request.setEmail("victormsonter@gmail.com");
        request.setAmount(100.00);
        InitializePaymentResponse response1 = paystackAdapter.initializePayment(request);
        VerifyPaymentResponse response = paystackAdapter.Verification(response1.getData().getReference());
        assertThat(response).isNotNull();
        assertThat(response.getData()).isNotNull();
    }


}