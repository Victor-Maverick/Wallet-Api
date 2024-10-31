package africa.semicolon.walletapi.infrastructure.adapter.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @OneToOne
    private WalletEntity walletEntity;
}
