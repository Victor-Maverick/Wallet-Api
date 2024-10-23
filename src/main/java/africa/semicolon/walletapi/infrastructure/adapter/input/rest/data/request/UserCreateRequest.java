package africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request;

import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String pin;
}
