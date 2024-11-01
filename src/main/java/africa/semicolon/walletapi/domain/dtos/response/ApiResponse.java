package africa.semicolon.walletapi.domain.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean isSuccessful;
    private T data;
}
