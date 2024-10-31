package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WalletPersistenceMapper {
    WalletEntity toWalletEntity(Wallet wallet);
    Wallet toWallet(WalletEntity walletEntity);
}
