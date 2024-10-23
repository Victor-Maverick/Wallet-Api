package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.userUseCases.DeleteUserUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.GetUserUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.SaveUseCase;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.exception.UserNotFoundException;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;

public class UserService implements SaveUseCase, GetUserUseCase, DeleteUserUseCase {
    private final UserOutputPort userOutputPort;
    private final WalletOutputPort walletOutputPort;


    public UserService(UserOutputPort userOutputPort, WalletOutputPort walletOutputPort) {
        this.userOutputPort = userOutputPort;
        this.walletOutputPort = walletOutputPort;
    }

    @Override
    public void deleteUser(User user) {
        userOutputPort.deleteUser(user);
    }

    @Override
    public User getUser(String email) {
        return userOutputPort.getByEmail(email).orElseThrow(()->new UserNotFoundException("user not found"));
    }

    @Override
    public User save(User user) {
        user = userOutputPort.save(user);
        Wallet wallet = new Wallet();
        walletOutputPort.saveWallet(wallet);
        return user;
    }

}
