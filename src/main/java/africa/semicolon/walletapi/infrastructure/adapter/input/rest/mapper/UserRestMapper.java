package africa.semicolon.walletapi.infrastructure.adapter.input.rest.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UserCreateRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response.UserCreateResponse;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response.UserQueryResponse;
import org.mapstruct.Mapper;

@Mapper
public interface UserRestMapper {
    User toUser(UserCreateRequest userCreateRequest);
    UserCreateResponse toUserCreateResponse(User user);
    UserQueryResponse toUserQueryResponse(User user);
}
