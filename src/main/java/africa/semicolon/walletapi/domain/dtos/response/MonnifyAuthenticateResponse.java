package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class MonnifyAuthenticateResponse {
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
    public static class ResponseBody{
        private String accessToken;
        private int expiresIn;
    }
}
