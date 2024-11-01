package africa.semicolon.walletapi.infrastructure.adapter.input.rest.mapper;

import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UpdateUserRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UpdateUserResponse;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UserCreateRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response.UserCreateResponse;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.response.UserQueryResponse;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class UserRestMapperImpl implements UserRestMapper {

    @Override
    public UserCreateResponse toUserCreateResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserCreateResponse.UserCreateResponseBuilder userCreateResponse = UserCreateResponse.builder();

        userCreateResponse.userId( user.getUserId() );
        userCreateResponse.walletId( userWalletWalletId( user ) );
        userCreateResponse.balance( userWalletBalance( user ) );
        userCreateResponse.userAuthId( user.getUserAuthId() );

        return userCreateResponse.build();
    }

    @Override
    public UserQueryResponse toUserQueryResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserQueryResponse.UserQueryResponseBuilder userQueryResponse = UserQueryResponse.builder();

        userQueryResponse.firstName( user.getFirstName() );
        userQueryResponse.lastName( user.getLastName() );
        userQueryResponse.email( user.getEmail() );

        return userQueryResponse.build();
    }

    @Override
    public User toUser(UserCreateRequest userCreateRequest) {
        if ( userCreateRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.role( userCreateRequest.getRole() );
        user.firstName( userCreateRequest.getFirstName() );
        user.lastName( userCreateRequest.getLastName() );
        user.email( userCreateRequest.getEmail() );
        user.password( userCreateRequest.getPassword() );

        return user.build();
    }

    @Override
    public User toUser(UpdateUserRequest updateUserRequest) {
        if ( updateUserRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( updateUserRequest.getNewFirstName() );
        user.lastName( updateUserRequest.getNewLastName() );
        user.password( updateUserRequest.getNewPassword() );

        return user.build();
    }

    @Override
    public UpdateUserResponse toUpdateUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UpdateUserResponse updateUserResponse = new UpdateUserResponse();

        updateUserResponse.setNewFirstname( user.getFirstName() );
        updateUserResponse.setNewLastname( user.getLastName() );

        return updateUserResponse;
    }

    private Long userWalletWalletId(User user) {
        Wallet wallet = user.getWallet();
        if ( wallet == null ) {
            return null;
        }
        return wallet.getWalletId();
    }

    private BigDecimal userWalletBalance(User user) {
        Wallet wallet = user.getWallet();
        if ( wallet == null ) {
            return null;
        }
        return wallet.getBalance();
    }
}
