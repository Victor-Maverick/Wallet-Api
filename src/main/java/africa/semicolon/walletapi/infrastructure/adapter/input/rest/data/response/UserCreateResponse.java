package africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateResponse {
    private Long id;
    private Long walletId;
    private String message;
}
