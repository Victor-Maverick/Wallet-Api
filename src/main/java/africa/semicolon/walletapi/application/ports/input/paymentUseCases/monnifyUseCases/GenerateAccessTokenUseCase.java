package africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases;

import africa.semicolon.walletapi.domain.dtos.response.MonnifyAuthenticateResponse;

public interface GenerateAccessTokenUseCase {
    MonnifyAuthenticateResponse generateAccessToken();
}
