package africa.semicolon.walletapi.application.ports.input.walletUseCases;


import africa.semicolon.walletapi.domain.model.Wallet;

public interface CreateWalletUseCase {
    Wallet saveWallet(Wallet wallet);
}
