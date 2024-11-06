package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases.GenerateAccessTokenUseCase;
import africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases.InitializeDepositUseCase;
import africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases.InitiateTransferUseCase;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.InitiateMonnifyTransactionRequest;
import africa.semicolon.walletapi.domain.dtos.request.MonnifyTransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitiateMonnifyTransactionResponse;
import africa.semicolon.walletapi.domain.dtos.response.MonnifyAuthenticateResponse;
import africa.semicolon.walletapi.domain.dtos.response.MonnifyTransferResponse;
import africa.semicolon.walletapi.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@RequiredArgsConstructor
public class MonnifyService implements GenerateAccessTokenUseCase, InitializeDepositUseCase, InitiateTransferUseCase {

    @Value("${monnify.api.key}")
    private String monnifyApiKey;

    @Value("${monnify.secret.key}")
    private String monnifyApiSecret;

    @Value("${monnify.base_url}")
    private String baseUrl;

    @Value("${monnify.login_url}")
    private String loginUrl;

    @Value("${monnify.contract_code}")
    private String contractCode;
    @Value("${monnify.transaction.redirect_url}")
    private String redirectUrl;

    private final RestTemplate restTemplate;
    private final UserOutputPort userOutputPort;

    @Override
    public MonnifyAuthenticateResponse generateAccessToken() {
        String encodedCredentials = monnifyApiKey + ":" + monnifyApiSecret;
        encodedCredentials = Base64.getEncoder().encodeToString(encodedCredentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodedCredentials);
        HttpEntity<String> entity = new HttpEntity<>("{}", headers);
        ResponseEntity<MonnifyAuthenticateResponse> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, MonnifyAuthenticateResponse.class);
        return response.getBody();
    }


    @Override
    public InitiateMonnifyTransactionResponse initializeMonnifyTransaction(InitiateMonnifyTransactionRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        String initializationUrl = baseUrl + "/api/v1/merchant/transactions/init-transaction";
        String accessToken = getAccessToken();
        User user = userOutputPort.getByEmail(request.getEmail());
        String paymentReference = generateReference(user);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> body = new HashMap<>();
        body.put("amount", request.getAmount());
        body.put("customerName", user.getFirstName() + " " + user.getFirstName());
        body.put("customerEmail", request.getEmail());
        body.put("paymentReference", paymentReference);
        body.put("paymentDescription", request.getPaymentDescription());
        body.put("currencyCode", "NGN");
        body.put("contractCode", contractCode);
        body.put("redirectUrl", redirectUrl);
        body.put("paymentMethods", new String[]{"ACCOUNT_TRANSFER"});

        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(initializationUrl, httpEntity, String.class);
            return objectMapper.readValue(responseEntity.getBody(), InitiateMonnifyTransactionResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Monnify transaction", e);
        }
    }

    private String getAccessToken() {
        MonnifyAuthenticateResponse authenticateResponse = generateAccessToken();
        return authenticateResponse.getResponseBody().getAccessToken();
    }

    private static @NotNull String generateReference(User user) {
        return user.getUserId() + "-" + UUID.randomUUID();
    }


    @Override
    public MonnifyTransferResponse initiateTransfer(MonnifyTransferRequest request) {
        User user = userOutputPort.getByEmail(request.getEmail());
        String reference = generateReference(user);
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        String destinationAccountNumber = String.format("%010d", request.getDestinationWalletId());
        String sourceAccountNumber = String.format("%010d", user.getWallet().getWalletId());

        String requestBody = String.format(
                "{\"amount\": %.2f, \"reference\":\"%s\", \"narration\":\"%s\", \"destinationAccountNumber\":\"%s\", \"destinationBankCode\":\"057\", \"currency\":\"%s\", \"sourceAccountNumber\":\"%s\"}",
                request.getAmount(),
                reference,
                request.getNarration(),
                destinationAccountNumber,
                "NGN",
                sourceAccountNumber
        );
        String baseUrl = "https://api.monnify.com";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                baseUrl + "/api/v2/disbursements/single",
                HttpMethod.POST,
                entity,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        MonnifyTransferResponse transferResponse;
        try {
            transferResponse = objectMapper.readValue(responseEntity.getBody(), MonnifyTransferResponse.class);
            transferResponse.setRequestSuccessful(true);
        } catch (Exception e) {
            transferResponse = new MonnifyTransferResponse();
            transferResponse.setRequestSuccessful(false);
            transferResponse.setResponseMessage("Failed to parse response: " + e.getMessage());
            transferResponse.setResponseCode("PARSING_ERROR");
        }

        return transferResponse;
    }

}


