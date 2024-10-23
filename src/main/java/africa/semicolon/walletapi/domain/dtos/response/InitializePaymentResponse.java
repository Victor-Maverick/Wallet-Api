package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InitializePaymentResponse {
    private Boolean status;
    private String message;
    @JsonProperty("data")
    private Data data;
}
