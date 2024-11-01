package africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases;

import africa.semicolon.walletapi.domain.model.User;

public interface DeleteUserUseCase {
    void delete(String userId);
}
