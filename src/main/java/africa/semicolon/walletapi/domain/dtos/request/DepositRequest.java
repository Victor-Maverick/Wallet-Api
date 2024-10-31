package africa.semicolon.walletapi.domain.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    private double amount;
    private Long walletId;
    private String email;
}
