package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.model.User;
import org.keycloak.admin.client.resource.UserResource;

public interface IdentityManagementPort {
    String registerUserRepresentation(User user);
    LoginResponse login(LoginRequest loginRequest);
    void updateUserRepresentation(String email, User user);
    void delete(String userId);
    UserResource getUser(String userId);
    void deleteUser(String email);

}
