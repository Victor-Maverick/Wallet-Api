package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.UserEntity;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.WalletEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class UserPersistenceMapperImpl implements UserPersistenceMapper {

    @Override
    public UserEntity toUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setWalletEntity( walletToWalletEntity( user.getWallet() ) );
        userEntity.setUserId( user.getUserId() );
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setPassword( user.getPassword() );

        return userEntity;
    }

    @Override
    public User toUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.wallet( walletEntityToWallet( userEntity.getWalletEntity() ) );
        user.userId( userEntity.getUserId() );
        user.firstName( userEntity.getFirstName() );
        user.lastName( userEntity.getLastName() );
        user.email( userEntity.getEmail() );
        user.password( userEntity.getPassword() );

        return user.build();
    }

    protected WalletEntity walletToWalletEntity(Wallet wallet) {
        if ( wallet == null ) {
            return null;
        }

        WalletEntity walletEntity = new WalletEntity();

        walletEntity.setWalletId( wallet.getWalletId() );
        walletEntity.setBalance( wallet.getBalance() );
        walletEntity.setPin( wallet.getPin() );

        return walletEntity;
    }

    protected Wallet walletEntityToWallet(WalletEntity walletEntity) {
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
