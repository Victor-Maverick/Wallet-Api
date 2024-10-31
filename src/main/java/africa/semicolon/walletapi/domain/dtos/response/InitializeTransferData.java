package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeTransferData {
    private double amount;
    private Long senderWalletId;
    private Long receiverWalletId;
    private String source;
    private String reason;
}
