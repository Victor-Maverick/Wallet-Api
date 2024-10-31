package africa.semicolon.walletapi.domain.dtos.response;

import africa.semicolon.walletapi.domain.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalletResponse {
    private Long walletId;
    private BigDecimal balance;

    public WalletResponse(Wallet wallet) {
        this.walletId = wallet.getWalletId();
        this.balance = wallet.getBalance();

    }
}
