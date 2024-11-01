package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.model.User;

import java.util.List;

public interface UserOutputPort {
    User save(User user);
    User getById(Long id);

    User getByEmail(String email);

    void deleteUser(User user);

    boolean existsByEmail(String email);

    List<User> getAllUsers();
}
