package africa.semicolon.walletapi.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitializeTransferRequest {
    private Long senderWalletId;
    private Long receiverWalletId;
    private double amount;
    private String reason;
}
