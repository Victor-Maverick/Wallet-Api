package africa.semicolon.walletapi.domain.dtos.response;

import africa.semicolon.walletapi.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private WalletResponse walletResponse;

    public UserResponse(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.walletResponse = new WalletResponse(user.getWallet());
    }
}
