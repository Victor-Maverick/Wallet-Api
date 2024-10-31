package africa.semicolon.walletapi.domain.dtos.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationRequest {
    private String nin;
    private String imageUrl;
}
