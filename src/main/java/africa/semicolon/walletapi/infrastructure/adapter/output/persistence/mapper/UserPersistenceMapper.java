package africa.semicolon.walletapi.infrastructure.adapter.persistence.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserPersistenceMapper {
    @Mapping(target = "walletEntity", source = "user.wallet")
    UserEntity toUserEntity(User user);
    @Mapping(target = "wallet", source = "userEntity.walletEntity")
    User toUser(UserEntity userEntity);
}
