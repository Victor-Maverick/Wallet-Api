package africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases;

import africa.semicolon.walletapi.domain.model.User;

public interface UpdateUserRepresentationUseCase {
    void updateUserRepresentation(String email, User user);
}
