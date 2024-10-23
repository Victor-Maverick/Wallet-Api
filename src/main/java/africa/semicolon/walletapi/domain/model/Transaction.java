package africa.semicolon.walletapi.domain.model;

import africa.semicolon.walletapi.domain.constants.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private BigDecimal amount;
    private TransactionType type;
}
