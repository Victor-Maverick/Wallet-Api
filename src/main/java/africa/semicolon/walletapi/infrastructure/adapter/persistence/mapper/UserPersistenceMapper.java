package africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    User toUser(UserEntity userEntity);
}
