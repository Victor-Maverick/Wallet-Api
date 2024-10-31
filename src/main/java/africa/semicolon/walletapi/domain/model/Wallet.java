package africa.semicolon.walletapi.domain.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Wallet {
    private Long walletId;
    private BigDecimal balance;
    private String pin;
    @OneToMany(fetch = FetchType.EAGER, cascade = ALL)
    private List<Transaction> transactions = new ArrayList<>();
}
