package africa.semicolon.walletapi.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DepositResponse {
    private boolean isSuccessful;
    private String message;
    private BigDecimal amount;
}
