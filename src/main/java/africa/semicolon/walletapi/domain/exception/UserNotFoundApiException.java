package africa.semicolon.walletapi.domain.exception;

public class UserNotFoundApiException extends WalletApiException {
    public UserNotFoundApiException(String message){
        super(message);
    }
}
