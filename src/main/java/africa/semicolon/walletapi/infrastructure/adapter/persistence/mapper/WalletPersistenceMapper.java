package africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper;

import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;

@Mapper
public interface WalletPersistenceMapper {
    WalletEntity toWalletEntity(Wallet wallet);
    Wallet toWallet(WalletEntity walletEntity);
}
