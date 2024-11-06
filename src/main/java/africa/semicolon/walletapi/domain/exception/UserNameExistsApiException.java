package africa.semicolon.walletapi.domain.exception;


public class UserNameExistsApiException extends WalletApiException {
    public UserNameExistsApiException(String message) {
        super(message);
    }
}
