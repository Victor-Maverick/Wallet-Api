package africa.semicolon.walletapi.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MonnifyTransferRequest {
    private String email;
    private double amount;
    private String narration;
    private Long destinationWalletId;
}
