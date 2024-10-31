package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.model.Wallet;

import java.util.Optional;

public interface WalletOutputPort {
    Optional<Wallet> getWallet(Long id);
    Wallet saveWallet(Wallet wallet);
}
