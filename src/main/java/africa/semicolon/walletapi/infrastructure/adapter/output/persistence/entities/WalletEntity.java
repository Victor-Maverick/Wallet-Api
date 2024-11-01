package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities;

import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "wallet", cascade = ALL, fetch = FetchType.LAZY)
    private List<TransactionEntity> transactions = new ArrayList<>();
}
