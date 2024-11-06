package africa.semicolon.walletapi.infrastructure.adapter.exception;

import africa.semicolon.walletapi.domain.dtos.response.ApiResponse;
import africa.semicolon.walletapi.domain.exception.WalletApiException;
import africa.semicolon.walletapi.domain.exception.WalletNoFoundApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CustomExceptionAdapter {
    @ExceptionHandler(WalletApiException.class)
    public final ResponseEntity<?> handleTransactionNotFoundException(WalletNoFoundApiException exception) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse(false,exception.getMessage()));
    }


}
