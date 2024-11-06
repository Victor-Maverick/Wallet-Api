package africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserPersistenceMapper {
    UserEntity toUserEntity(User user);
    @Mapping(target = "wallet", source = "userEntity.wallet")
    @Mapping(target = "userId", source = "userEntity.userId")
    User toUser(UserEntity userEntity);
}
