package africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String newFirstName;
    private String newLastName;
    private String newPassword;
}
