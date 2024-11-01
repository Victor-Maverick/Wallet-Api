package africa.semicolon.walletapi.infrastructure.adapter.output.persistence;

import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.UserEntity;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    public User getById(Long id) {
        final UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()-> new PiggyWalletException("user not found"));
        return this.userPersistenceMapper.toUser(userEntity);
    }

    @Override
    public User getByEmail(String email) {
        final UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(()-> new PiggyWalletException("user not found"));
        return this.userPersistenceMapper.toUser(userEntity);
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

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userEntityList.stream()
                .map(userPersistenceMapper::toUser)
                .collect(Collectors.toList());
    }
}
