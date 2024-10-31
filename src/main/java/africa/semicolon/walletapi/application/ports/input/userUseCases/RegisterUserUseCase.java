package africa.semicolon.walletapi.application.ports.input.userUseCases;

import africa.semicolon.walletapi.domain.model.User;

public interface RegisterUserUseCase {
    User register(User user);
}
