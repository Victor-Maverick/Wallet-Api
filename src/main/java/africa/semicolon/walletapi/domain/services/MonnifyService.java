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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(MonnifyService.class);
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
        String encodedCredentials = monnifyApiKey +":"+ monnifyApiSecret;
        encodedCredentials = Base64.getEncoder().encodeToString(encodedCredentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Basic " + encodedCredentials);
        HttpEntity<String> entity = new HttpEntity<>("{}",headers);
        ResponseEntity<MonnifyAuthenticateResponse> response = restTemplate.exchange(loginUrl, HttpMethod.POST, entity, MonnifyAuthenticateResponse.class);
        return response.getBody();

    }
}
