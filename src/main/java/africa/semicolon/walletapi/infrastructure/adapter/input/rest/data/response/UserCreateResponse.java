package africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateResponse {
    private Long userId;
    private Long walletId;
    private String userAuthId;
    private BigDecimal balance;
}
