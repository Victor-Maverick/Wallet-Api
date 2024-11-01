package africa.semicolon.walletapi.infrastructure.adapter.input.rest;

import africa.semicolon.walletapi.application.ports.input.premblyUseCases.VerifyNinAndFaceUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.DeleteUserUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.GetAllUsersUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.RegisterUserUseCase;
import africa.semicolon.walletapi.domain.dtos.request.LoginRequest;
import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;
import africa.semicolon.walletapi.domain.dtos.response.ApiResponse;
import africa.semicolon.walletapi.domain.dtos.response.LoginResponse;
import africa.semicolon.walletapi.domain.dtos.response.UserResponse;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import africa.semicolon.walletapi.domain.model.APIConstants;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.domain.services.UserService;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UpdateUserRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.data.request.UserCreateRequest;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserRestAdapter {
    private final UserRestMapper userRestMapper;
    private final UserService userService;
    private final RegisterUserUseCase registerUserUseCase;
    private final VerifyNinAndFaceUseCase verifyNinAndFaceUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserCreateRequest request){
        try {
            User user = userRestMapper.toUser(request);
            user = registerUserUseCase.register(user);
            return new ResponseEntity<>(new ApiResponse<>(true,userRestMapper.toUserCreateResponse(user)), CREATED);
        }
        catch(PiggyWalletException exception){
            return new ResponseEntity<>(new ApiResponse<>(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            LoginResponse loginResponse = userService.login(request);
            return new ResponseEntity<>(new ApiResponse<>(true,loginResponse), OK);
        }
        catch (PiggyWalletException exception){
            return new ResponseEntity<>(new ApiResponse<>(false,exception.getMessage()), BAD_REQUEST);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestParam String email, @RequestBody UpdateUserRequest updateUserRequest){
        try{
            User user = userRestMapper.toUser(updateUserRequest);
            userService.updateUser(email,user);
            return new ResponseEntity<>(new ApiResponse<>(true,userRestMapper.toUpdateUserResponse(user)), OK);
        }catch(PiggyWalletException exception){
            return new ResponseEntity<>(new ApiResponse<>(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<?> findUser(@RequestParam String email){
        try{
            UserResponse response = userService.getUser(email);
            return new ResponseEntity<>(new ApiResponse<>(true,response), OK);
        }
        catch (PiggyWalletException exception){
            return new ResponseEntity<>(new ApiResponse<>(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(new ApiResponse<>(true,userService.getAllUsers()), OK);
    }

    @PostMapping(value = "/verifyNinAndFace", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> verifyNinAndFace(
            @RequestParam("nin") String nin,
            @RequestParam("email") String email,
            @RequestParam("image") MultipartFile image) {
        try {
            VerificationRequest request = new VerificationRequest(nin, email, image);
            String response = verifyNinAndFaceUseCase.verifyNinAndFace(request);
            return new ResponseEntity<>(new ApiResponse<>(true,response), OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ApiResponse<>(false,exception.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try {
            deleteUserUseCase.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        catch (PiggyWalletException exception){
            return new ResponseEntity<>(exception.getMessage(), BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAllUsers(){
        List<UserResponse> users = getAllUsersUseCase.getAllUsers();
        return new ResponseEntity<>(new ApiResponse<>(true,users), OK);
    }


}
