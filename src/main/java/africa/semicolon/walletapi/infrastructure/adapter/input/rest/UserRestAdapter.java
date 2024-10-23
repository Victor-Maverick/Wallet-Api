package africa.semicolon.walletapi.infrastructure.adapter.input.rest;


import africa.semicolon.walletapi.application.ports.input.userUseCases.GetUserUseCase;
import africa.semicolon.walletapi.application.ports.input.userUseCases.SaveUseCase;
import africa.semicolon.walletapi.infrastructure.adapter.input.rest.mapper.UserRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestAdapter {
    private final SaveUseCase saveUseCase;
    private final GetUserUseCase getUserUseCase;
    private final UserRestMapper userRestMapper;
}
