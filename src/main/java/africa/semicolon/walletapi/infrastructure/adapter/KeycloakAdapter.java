package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.application.ports.output.IdentityManagementPort;
import africa.semicolon.walletapi.domain.constants.Role;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.exception.WalletApiException;
import africa.semicolon.walletapi.domain.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class KeycloakAdapter implements IdentityManagementPort {
    private static final Logger log = LoggerFactory.getLogger(KeycloakAdapter.class);
    @Value("${app.keycloak.realm}")
    private String realm;
    @Value("${app.keycloak.admin.clientId}")
    private String clientId;
    private final Keycloak keycloak;
    @Value("${token.url}")
    private String tokenUrl;
    @Value("${app.keycloak.admin.secretKey}")
    private String clientSecret;

    public KeycloakAdapter(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    @Override
    public String registerUserRepresentation(User user) {
        UserRepresentation userRepresentation = buildUserRepresentation(user);
        buildCredentialRepresentation(user, userRepresentation);
        getUsersResource().create(userRepresentation);
        UserRepresentation userRepresentation1 = getUserRepresentation(user.getEmail());
        assignRole(userRepresentation1.getId(), user.getRole());
        return userRepresentation1.getId();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = buildParams(loginRequest);
        return getLoginResponse(restTemplate, params, tokenUrl);
    }

    public static LoginResponse getLoginResponse(RestTemplate restTemplate, MultiValueMap<String, String> params, String tokenUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        try {
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response.getBody(), LoginResponse.class);
        } catch (JsonProcessingException e) {
            throw new WalletApiException("Failed to process login response");
        } catch (HttpClientErrorException e) {
            throw new WalletApiException("Invalid credentials");
        }
    }

    private @NotNull MultiValueMap<String, String> buildParams(LoginRequest loginRequest) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("username", loginRequest.getUsername());
        params.add("password", loginRequest.getPassword());
        return params;
    }

    private static void buildCredentialRepresentation(User user, UserRepresentation userRepresentation) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(user.getPassword());
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
    }

    @Override
    public void updateUserRepresentation(String email, User user) {
        UsersResource usersResource = getUsersResource();
        UserRepresentation userRepresentation = updateUserDetails(email, user);
        updateUserCredentialRepresentation(user, userRepresentation);
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    private static void updateUserCredentialRepresentation(User user, UserRepresentation userRepresentation) {
        CredentialRepresentation passwordRepresentation = new CredentialRepresentation();
        passwordRepresentation.setType(CredentialRepresentation.PASSWORD);
        passwordRepresentation.setTemporary(false);
        passwordRepresentation.setValue(user.getPassword());
        userRepresentation.setCredentials(List.of(passwordRepresentation));
    }

    private @NotNull UserRepresentation updateUserDetails(String email, User user) {
        UserRepresentation userRepresentation = getUserRepresentation(email);
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        return userRepresentation;
    }

    @Override
    public void delete(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.delete(userId);
    }

    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    @Override
    public void deleteUser(String email) {
        UserRepresentation userRepresentation = getUserRepresentation(email);
        delete(userRepresentation.getId());

    }

    private static UserRepresentation buildUserRepresentation(User user) {
        UserRepresentation userRepresentation = new UserRepresentation();
        setUserUserRepresentation(user, userRepresentation);
        return userRepresentation;
    }

    private static void setUserUserRepresentation(User user, UserRepresentation userRepresentation) {
        userRepresentation.setEnabled(true);
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setUsername(user.getEmail());
        userRepresentation.setEmailVerified(false);
    }

    private UsersResource getUsersResource(){
        return keycloak.realm(realm).users();
    }
    private RolesResource getRolesResource(){
        return keycloak.realm(realm).roles();
    }


    private void assignRole(String userId, Role role) {
        RolesResource rolesResource = getRolesResource();
        UserResource user = getUser(userId);
        RoleRepresentation keycloakRole = rolesResource.get(String.valueOf(role)).toRepresentation();
        user.roles().realmLevel().add(Collections.singletonList(keycloakRole));
    }
    private UserRepresentation getUserRepresentation(String email) {
        List<UserRepresentation> users = getUsersResource().searchByUsername(email,true);
        return users.get(0);
    }
}
