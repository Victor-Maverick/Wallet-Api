package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponse {
    @JsonProperty("responseCode")
    private String responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("responseBody")
    private ResponseBody responseBody;

    public String getAccessToken() {
        return responseBody != null ? responseBody.getAccessToken() : null;
    }

    @Getter
    @Setter
    public static class ResponseBody {
        @JsonProperty("accessToken")
        private String accessToken;
        @JsonProperty("expiresIn")
        private String expiresIn;
    }

}
