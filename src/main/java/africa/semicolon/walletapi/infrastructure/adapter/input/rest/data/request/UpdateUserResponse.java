package africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserResponse {
    private String newFirstname;
    private String newLastname;
}
