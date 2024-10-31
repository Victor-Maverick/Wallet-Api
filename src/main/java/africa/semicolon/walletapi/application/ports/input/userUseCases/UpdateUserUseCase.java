package africa.semicolon.walletapi.application.ports.input.userUseCases;


import africa.semicolon.walletapi.domain.model.User;

public interface UpdateUserUseCase {
    User updateUser(String email, User user);
}
