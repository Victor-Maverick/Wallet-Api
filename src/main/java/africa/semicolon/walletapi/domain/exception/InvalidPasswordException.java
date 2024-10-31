package africa.semicolon.walletapi.domain.exception;

public class InvalidPasswordException extends PiggyWalletException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
