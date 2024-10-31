package africa.semicolon.walletapi.application.ports.input.userUseCases;

import africa.semicolon.walletapi.domain.dtos.response.UserResponse;
import africa.semicolon.walletapi.domain.model.User;

public interface GetUserUseCase {
    UserResponse getUser(String email);
}
