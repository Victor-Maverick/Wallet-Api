package africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases;

import org.keycloak.admin.client.resource.UserResource;

public interface GetUserResourceUseCase {
    UserResource getUser(String userId);
}
