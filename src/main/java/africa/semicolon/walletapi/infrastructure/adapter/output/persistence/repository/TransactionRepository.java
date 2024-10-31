package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("select t from TransactionEntity t where t.wallet.walletId=:walletId")
    List<TransactionEntity> findAllForWallet(Long walletId);
}
