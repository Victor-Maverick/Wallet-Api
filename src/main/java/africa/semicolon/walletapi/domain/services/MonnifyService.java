package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases.GenerateAccessTokenUseCase;
import africa.semicolon.walletapi.domain.dtos.response.AccessTokenResponse;
import africa.semicolon.walletapi.domain.dtos.response.MonnifyAuthenticateResponse;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
public class MonnifyService implements GenerateAccessTokenUseCase {

    @Value("${monnify.api.key}")
    private String monnifyApiKey;

    @Value("${monnify.secret.key}")
    private String monnifyApiSecret;

    @Value("${monnify.base-url}")
    private String baseUrl;

    @Value("${monnify.login_url}")
    private String loginUrl;

    private final RestTemplate restTemplate;

    @Override
    public MonnifyAuthenticateResponse generateAccessToken() {
        ObjectMapper objectMapper = new ObjectMapper();
        String authenticationUrl = baseUrl + "/auth/token";
        String credentials = monnifyApiKey + ":" + monnifyApiSecret;
        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(loginUrl);
        request.setHeader("Authorization", "Basic " + base64Credentials);
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = (HttpEntity) response.getEntity();
            String jsonResponse = EntityUtils.toString((org.apache.http.HttpEntity) entity);
            return objectMapper.readValue(jsonResponse, MonnifyAuthenticateResponse.class);
        } catch (IOException exception) {
            throw new PiggyWalletException(exception.getMessage());
        }


    }
}
