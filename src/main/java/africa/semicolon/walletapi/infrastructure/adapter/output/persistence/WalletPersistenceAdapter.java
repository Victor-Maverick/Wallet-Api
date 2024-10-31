package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletOutputPort {
    private final WalletRepository walletRepository;
    private final WalletPersistenceMapper walletPersistenceMapper;

    @Override
    public Optional<Wallet> getWallet(Long id) {
        final Optional<WalletEntity> walletEntity = walletRepository.findById(id);
        if (walletEntity.isEmpty()) {
            return Optional.empty();
        }
        final Wallet wallet = this.walletPersistenceMapper.toWallet(walletEntity.get());
        return Optional.of(wallet);
    }

    @Override
    public Wallet saveWallet(Wallet wallet) {
        WalletEntity walletEntity = walletPersistenceMapper.toWalletEntity(wallet);
        walletEntity = this.walletRepository.save(walletEntity);
        return walletPersistenceMapper.toWallet(walletEntity);
    }
}
