package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class InitiateMonnifyTransactionResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ResponseBody responseBody;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    public static class ResponseBody{
        private String transactionReference;
        private String paymentReference;
        private String[] enabledPaymentMethod;
    }

}
