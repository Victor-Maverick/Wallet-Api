package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.application.ports.output.PaymentPort;
import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.InitializeTransferResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static africa.semicolon.walletapi.domain.model.APIConstants.*;

@RequiredArgsConstructor
public class PaystackAdapter implements PaymentPort {
    private final RestTemplate restTemplate;

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentRequest request) {
        InitializePaymentResponse initializePaymentResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(request));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) result.append(line);

            } else throw new Exception("Paystack is unable to initialize payment at the moment");

            ObjectMapper mapper = new ObjectMapper();
            initializePaymentResponse = mapper.readValue(result.toString(), InitializePaymentResponse.class);
            initializePaymentResponse.getData().setAmount(request.getAmount());
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return initializePaymentResponse;
    }

    @Override
    public VerifyPaymentResponse Verification(String reference) throws Exception {

        VerifyPaymentResponse paymentVerificationResponse = null;

        try{
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + SECRET_KEY);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine(). getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), VerifyPaymentResponse.class);
            if (paymentVerificationResponse == null || !paymentVerificationResponse.isStatus()) {
                throw new Exception("An error");
            }
        } catch (Exception ex) {
            throw new Exception("Error: "+ex);
        }
        System.out.println(paymentVerificationResponse.getData().getTransferCode());
        return paymentVerificationResponse;
    }

    @Override
    public InitializeTransferResponse initializeTransfer(InitializeTransferRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(SECRET_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("source", "balance");
        requestBody.put("reason", request.getReason());
        requestBody.put("amount", request.getAmount());
        requestBody.put("recipient", request.getReceiverWalletId());

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(PAYSTACK_INITIALIZE_TRANSFER, HttpMethod.POST, requestEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        InitializeTransferResponse initializeTransferResponse = null;
        try {
            initializeTransferResponse = objectMapper.readValue(responseEntity.getBody(), InitializeTransferResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return initializeTransferResponse;
    }
}
