package africa.semicolon.walletapi.domain.model;

import africa.semicolon.walletapi.domain.constants.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Wallet wallet;
    private String userAuthId;
}
