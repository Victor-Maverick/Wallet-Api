package africa.semicolon.walletapi.domain.dtos.response;

import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private Long walletId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public TransactionResponse(TransactionEntity transaction) {
        this.id = transaction.getId();
        this.walletId = transaction.getWallet().getWalletId();
        this.amount = transaction.getAmount();
        this.timestamp = transaction.getTimestamp();
    }
}
