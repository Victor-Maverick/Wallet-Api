package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyPaymentResponse {
    private boolean status;
    private String message;
    private VerifyData data;
}
