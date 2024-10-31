package africa.semicolon.walletapi.infrastructure.adapter.input.rest.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UpdateUserRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UpdateUserResponse;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UserCreateRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response.UserCreateResponse;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response.UserQueryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface UserRestMapper {

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "walletId", source = "user.wallet.walletId")
    @Mapping(target = "balance", source = "user.wallet.balance")
    UserCreateResponse toUserCreateResponse(User user);

    UserQueryResponse toUserQueryResponse(User user);

    @Mapping(target = "role", source = "userCreateRequest.role")
    User toUser(UserCreateRequest userCreateRequest);

    @Mapping(target = "firstName", source = "updateUserRequest.newFirstName")
    @Mapping(target = "lastName", source = "updateUserRequest.newLastName")
    @Mapping(target = "password", source = "updateUserRequest.newPassword")
    User toUser(UpdateUserRequest updateUserRequest);

    @Mapping(target = "newFirstname", source = "user.firstName")
    @Mapping(target = "newLastname", source = "user.lastName")
    UpdateUserResponse toUpdateUserResponse(User user);
}
