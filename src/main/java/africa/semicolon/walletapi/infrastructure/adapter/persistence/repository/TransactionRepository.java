package africa.semicolon.walletapi.infrastructure.adapter.persistence.repository;

import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

}
