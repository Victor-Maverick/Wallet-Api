package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.WalletEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class TransactionPersistenceMapperImpl implements TransactionPersistenceMapper {

    @Override
    public TransactionEntity toTransactionEntity(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setId( transaction.getId() );
        transactionEntity.setAmount( transaction.getAmount() );
        transactionEntity.setType( transaction.getType() );
        transactionEntity.setWallet( walletToWalletEntity( transaction.getWallet() ) );

        return transactionEntity;
    }

    @Override
    public Transaction toTransaction(TransactionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId( entity.getId() );
        transaction.setAmount( entity.getAmount() );
        transaction.setType( entity.getType() );
        transaction.setWallet( walletEntityToWallet( entity.getWallet() ) );

        return transaction;
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
