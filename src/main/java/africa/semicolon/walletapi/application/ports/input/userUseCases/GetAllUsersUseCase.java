package africa.semicolon.walletapi.application.ports.input.userUseCases;

import africa.semicolon.walletapi.domain.dtos.response.UserResponse;

import java.util.List;

public interface GetAllUsersUseCase {
    List<UserResponse> getAllUsers();
}
