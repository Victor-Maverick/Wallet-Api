package africa.semicolon.walletapi.domain.model;

import lombok.*;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@ToString
public class Wallet {
    private Long walletId;
    private BigDecimal balance;
    private String pin;
}
