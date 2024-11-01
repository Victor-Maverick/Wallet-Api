package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.userUseCases.*;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.dtos.response.UserResponse;
import africa.semicolon.walletapi.domain.exception.InvalidPasswordException;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import africa.semicolon.walletapi.domain.exception.UserNameExistsException;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.model.Wallet;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
public class UserService implements RegisterUserUseCase, LoginUseCase, GetUserUseCase, DeleteUserUseCase, UpdateUserUseCase, GetAllUsersUseCase {
    private final PasswordEncoder passwordEncoder;
    private final UserOutputPort userOutputPort;
    private final WalletOutputPort walletOutputPort;
    private final AuthService authService;

    @Override
    public User register(User user) {
        User savedUser = buildRegistration(user);
        savedUser.setRole(user.getRole());
        authService.assignRole(savedUser.getEmail(), savedUser.getRole());
        return savedUser;
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
            throw new PiggyWalletException("fields should not be empty");
    }

    private void validateEmail(String email) {
        boolean existsByEmail = userOutputPort.existsByEmail(email);
        if (existsByEmail)
            throw new UserNameExistsException(email+" already exists");
    }


    public UserService(PasswordEncoder passwordEncoder, UserOutputPort userOutputPort, WalletOutputPort walletOutputPort, AuthService authService) {
        this.passwordEncoder = passwordEncoder;
        this.userOutputPort = userOutputPort;
        this.walletOutputPort = walletOutputPort;
        this.authService = authService;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userOutputPort.getById(userId);
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
        return authService.registerUserRepresentation(user);
    }


    @Override
    public User updateUser(String email, User user) {
        User userToUpdate = getUserByEmail(email);
        validateAndMap(user, userToUpdate);
        authService.updateUserRepresentation(email, userToUpdate);
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
            throw new InvalidPasswordException("invalid credentials");
        return authService.login(loginRequest);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userOutputPort.getAllUsers();
        if(users.isEmpty())return List.of();
        return users.stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }


}
