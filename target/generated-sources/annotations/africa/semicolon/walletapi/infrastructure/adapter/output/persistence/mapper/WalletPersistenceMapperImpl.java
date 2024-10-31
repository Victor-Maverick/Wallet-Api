package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.WalletEntity;
import java.util.ArrayList;
import java.util.List;
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
        walletEntity.setTransactions( transactionListToTransactionEntityList( wallet.getTransactions() ) );

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
        wallet.setTransactions( transactionEntityListToTransactionList( walletEntity.getTransactions() ) );

        return wallet;
    }

    protected TransactionEntity transactionToTransactionEntity(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setId( transaction.getId() );
        transactionEntity.setAmount( transaction.getAmount() );
        transactionEntity.setType( transaction.getType() );
        transactionEntity.setWallet( toWalletEntity( transaction.getWallet() ) );

        return transactionEntity;
    }

    protected List<TransactionEntity> transactionListToTransactionEntityList(List<Transaction> list) {
        if ( list == null ) {
            return null;
        }

        List<TransactionEntity> list1 = new ArrayList<TransactionEntity>( list.size() );
        for ( Transaction transaction : list ) {
            list1.add( transactionToTransactionEntity( transaction ) );
        }

        return list1;
    }

    protected Transaction transactionEntityToTransaction(TransactionEntity transactionEntity) {
        if ( transactionEntity == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId( transactionEntity.getId() );
        transaction.setAmount( transactionEntity.getAmount() );
        transaction.setType( transactionEntity.getType() );
        transaction.setWallet( toWallet( transactionEntity.getWallet() ) );

        return transaction;
    }

    protected List<Transaction> transactionEntityListToTransactionList(List<TransactionEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<Transaction> list1 = new ArrayList<Transaction>( list.size() );
        for ( TransactionEntity transactionEntity : list ) {
            list1.add( transactionEntityToTransaction( transactionEntity ) );
        }

        return list1;
    }
}
