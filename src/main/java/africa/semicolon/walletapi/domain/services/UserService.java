package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.userUseCases.*;
import africa.semicolon.walletapi.application.ports.input.userUseCases.identityVerificationUseCases.VerifyNinAndFaceUseCase;
import africa.semicolon.walletapi.application.ports.output.IdentityManagementPort;
import africa.semicolon.walletapi.application.ports.output.IdentityVerificationPort;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.dtos.response.UserResponse;
import africa.semicolon.walletapi.domain.exception.InvalidPasswordApiException;
import africa.semicolon.walletapi.domain.exception.WalletApiException;
import africa.semicolon.walletapi.domain.exception.UserNameExistsApiException;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequiredArgsConstructor
public class UserService implements RegisterUserUseCase, LoginUseCase, GetUserUseCase, DeleteUserUseCase, UpdateUserUseCase, GetAllUsersUseCase, VerifyNinAndFaceUseCase {
    PasswordEncoder passwordEncoder;
    UserOutputPort userOutputPort;
    WalletOutputPort walletOutputPort;
    IdentityManagementPort identityManagementPort;
    IdentityVerificationPort identityVerificationPort;

    @Override
    public User register(User user) {
        user = buildRegistration(user);
        user.setRole(user.getRole());
        return user;
    }

    private User buildRegistration(User user) {
        validateData(user);
        validateEmail(user.getEmail());
        String keycloakId = createUserRepresentation(user);
        user.setUserAuthId(keycloakId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userOutputPort.save(user);
        Wallet wallet = createWallet();
        user.setWallet(wallet);
        return userOutputPort.save(user);
    }

    private Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setPin(passwordEncoder.encode("0000"));
        wallet.setBalance(new BigDecimal("0.00"));
        wallet = walletOutputPort.saveWallet(wallet);
        return wallet;
    }

    private void validateData(User user) {
        if(user.getEmail().trim().isEmpty() || user.getPassword().trim().isEmpty() || user.getFirstName().trim().isEmpty() || user.getLastName().trim().isEmpty())
            throw new WalletApiException("fields should not be empty");
    }

    private void validateEmail(String email) {
        boolean existsByEmail = userOutputPort.existsByEmail(email);
        if (existsByEmail)
            throw new UserNameExistsApiException(email+" already exists");
    }


    @Override
    public void deleteUser(String email) {
        User user = userOutputPort.getByEmail(email);
        identityManagementPort.deleteUser(email);
        userOutputPort.deleteUser(user);
    }

    @Override
    public UserResponse getUser(String email) {
        User user = getUserByEmail(email);
        return new UserResponse(user);
    }

    private User getUserByEmail(String email) {
        return userOutputPort.getByEmail(email);
    }

    private String createUserRepresentation(User user) {
        return identityManagementPort.registerUserRepresentation(user);
    }


    @Override
    public User updateUser(String email, User user) {
        User userToUpdate = getUserByEmail(email);
        validateAndMap(user, userToUpdate);
        identityManagementPort.updateUserRepresentation(email, userToUpdate);
        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));
        return userOutputPort.save(userToUpdate);
    }

    private static void validateAndMap(User user, User userToUpdate) {
        if(!user.getFirstName().trim().isEmpty())
            userToUpdate.setFirstName(user.getFirstName());
        if(!user.getLastName().trim().isEmpty())
            userToUpdate.setLastName(user.getLastName());
        if(!user.getPassword().trim().isEmpty())
            userToUpdate.setPassword(user.getPassword());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = getUserByEmail(loginRequest.getUsername());
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new InvalidPasswordApiException("invalid credentials");
        return identityManagementPort.login(loginRequest);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userOutputPort.getAllUsers();
        if(users.isEmpty())return List.of();
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }


    @Override
    public String verifyNinAndFace(VerificationRequest request) throws IOException {
        return identityVerificationPort.verifyNinAndFace(request);
    }
}
