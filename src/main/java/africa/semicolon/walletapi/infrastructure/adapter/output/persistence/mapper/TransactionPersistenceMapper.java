package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionPersistenceMapper {
    TransactionEntity toTransactionEntity(Transaction transaction);
    Transaction toTransaction(TransactionEntity entity);
}
