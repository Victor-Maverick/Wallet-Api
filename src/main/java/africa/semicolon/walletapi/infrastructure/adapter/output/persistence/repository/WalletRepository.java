package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository;

import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

}
