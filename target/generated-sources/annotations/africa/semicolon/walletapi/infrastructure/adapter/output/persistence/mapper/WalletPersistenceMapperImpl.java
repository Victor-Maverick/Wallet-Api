package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.WalletEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class WalletPersistenceMapperImpl implements WalletPersistenceMapper {

    @Override
    public WalletEntity toWalletEntity(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletEntity walletEntity = new WalletEntity();

        walletEntity.setWalletId( wallet.getWalletId() );
        walletEntity.setBalance( wallet.getBalance() );
        walletEntity.setPin( wallet.getPin() );

        return walletEntity;
    }

    @Override
    public Wallet toWallet(WalletEntity walletEntity) {
        if ( walletEntity == null ) {
            return null;
        }

        Wallet wallet = new Wallet();

        wallet.setWalletId( walletEntity.getWalletId() );
        wallet.setBalance( walletEntity.getBalance() );
        wallet.setPin( walletEntity.getPin() );

        return wallet;
    }
}
