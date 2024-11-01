package africa.semicolon.walletapi.domain.dtos.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationRequest {
    private String nin;
    private String email;
    private MultipartFile image;
}
