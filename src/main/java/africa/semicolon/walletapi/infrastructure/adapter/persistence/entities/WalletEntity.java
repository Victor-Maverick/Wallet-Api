package africa.semicolon.walletapi.infrastructure.adapter.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    public Long id;
    private BigDecimal balance;
    private String pin;
}
