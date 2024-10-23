package africa.semicolon.walletapi.domain.dtos.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPaymentResponse {
    private boolean status;
    private String message;
    private Data data;
}
