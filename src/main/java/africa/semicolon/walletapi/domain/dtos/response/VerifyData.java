package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyData {
    private double amount;
    private String status;
    private String transferCode;
}
