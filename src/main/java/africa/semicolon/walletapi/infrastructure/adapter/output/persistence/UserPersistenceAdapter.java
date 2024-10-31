package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.UserEntity;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.WalletEntity;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper.WalletPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.repository.UserRepository;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.repository.WalletRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserOutputPort {
    private final UserRepository userRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userRepository.save(userPersistenceMapper.toUserEntity(user));
        return userPersistenceMapper.toUser(userEntity);
    }

    @Override
    public Optional<User> getById(Long id) {
        final Optional<UserEntity>userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) return Optional.empty();
        final User user = this.userPersistenceMapper.toUser(userEntity.get());
        return Optional.of(user);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        final Optional<UserEntity>userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) return Optional.empty();
        final User user = this.userPersistenceMapper.toUser(userEntity.get());
        return Optional.of(user);
    }

    @Override
    public void deleteUser(User user) {
        final UserEntity userEntity = userPersistenceMapper.toUserEntity(user);
        this.userRepository.delete(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
