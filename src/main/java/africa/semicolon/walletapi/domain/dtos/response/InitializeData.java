package africa.semicolon.walletapi.domain.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitializeData {
    private String reference;
    private double amount;
    @JsonProperty("authorization_url")
    private String authorizationUrl;
}
