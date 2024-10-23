package africa.semicolon.walletapi.domain.dtos.request;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitializePaymentRequest {
    private double amount;
    private String email;
}
