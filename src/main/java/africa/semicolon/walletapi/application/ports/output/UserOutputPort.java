package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.model.User;

import java.util.Optional;

public interface UserOutputPort {
    User save(User user);
    Optional<User> getById(Long id);

    Optional<User> getByEmail(String email);

    void deleteUser(User user);
}
