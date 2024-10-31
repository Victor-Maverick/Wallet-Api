package africa.semicolon.walletapi.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitializeTransferResponse {
    private boolean status;
    private String message;
    private InitializeTransferData data;
}
