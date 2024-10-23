package africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserQueryResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
