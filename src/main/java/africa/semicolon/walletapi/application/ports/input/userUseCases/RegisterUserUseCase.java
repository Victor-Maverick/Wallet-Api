package africa.semicolon.walletapi.application.ports.input.userUseCases;

import africa.semicolon.walletapi.domain.model.User;

public interface RegisterUseCase {
    User register(User user);
}
