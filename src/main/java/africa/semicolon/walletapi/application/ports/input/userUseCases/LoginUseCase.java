package africa.semicolon.walletapi.application.ports.input.userUseCases;

import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse login(LoginRequest loginRequest);
}
