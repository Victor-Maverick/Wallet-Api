package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MonnifyTransferResponse {
    private boolean requestSuccessful;
    private String responseMessage;
    private String responseCode;

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MonnifyAuthenticateResponse.ResponseBody responseBody;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ResponseBody{
        private double amount;
        private String reference;
        private String status;
        private double totalFee;
    }
}
