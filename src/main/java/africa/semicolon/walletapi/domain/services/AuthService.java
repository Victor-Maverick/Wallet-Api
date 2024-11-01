package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases.DeleteUserUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases.GetUserResourceUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases.RegisterUserRepresentationUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.keycloakUseCases.UpdateUserRepresentationUseCase;
import africa.semicolon.walletapi.domain.constants.Role;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import africa.semicolon.walletapi.domain.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
public class AuthService implements RegisterUserRepresentationUseCase, UpdateUserRepresentationUseCase, GetUserResourceUseCase, DeleteUserUseCase {
    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    private final Keycloak keycloak;
    @Value("${token.url}")
    private String tokenUrl;
    @Value("${app.keycloak.admin.secretKey}")
    private String clientSecret;

    public AuthService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }


    @Override
    public String registerUserRepresentation(User user) {
        UserRepresentation userRepresentation = buildUserRepresentation(user);
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource usersResource = getUsersResource();
        usersResource.create(userRepresentation);
        return userRepresentation.getId();
    }

    private static UserRepresentation buildUserRepresentation(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmailVerified(false);
        return userRepresentation;
    }

    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }


    public LoginResponse login(LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", loginRequest.getUsername());
        params.add("password", loginRequest.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), LoginResponse.class);
        } catch (JsonProcessingException e) {
            throw new PiggyWalletException("Failed to process login response");
        } catch (HttpClientErrorException e) {
            log.info(e.getResponseBodyAsString());
            throw new PiggyWalletException("Invalid credentials");
        }
    }

    @Override
    public void updateUserRepresentation(String email, User user) {
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = getUserRepresentation(email);
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        CredentialRepresentation passwordRepresentation = new CredentialRepresentation();
        passwordRepresentation.setType(CredentialRepresentation.PASSWORD);
        passwordRepresentation.setTemporary(false);
        passwordRepresentation.setValue(user.getPassword());
        userRepresentation.setCredentials(List.of(passwordRepresentation));
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    private UserRepresentation getUserRepresentation(String email) {
        List<UserRepresentation> users = getUsersResource().searchByUsername(email,true);
        return users.get(0);
    }

    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    public void assignRole(String email, Role role) {
        UserRepresentation userRepresentation = getUserRepresentation(email);
        RolesResource rolesResource = getRolesResource();
        UserResource user = getUser(userRepresentation.getId());
        RoleRepresentation keycloakRole = rolesResource.get(String.valueOf(role)).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(keycloakRole));
    }

    private RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }

    @Override
    public void delete(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }
}
