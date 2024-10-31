package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long walletId;
    private BigDecimal balance;
    private String pin;
    @OneToMany(fetch = EAGER, cascade = ALL)
    private List<TransactionEntity> transactions = new ArrayList<>();
}
