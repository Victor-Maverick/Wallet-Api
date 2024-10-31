package africa.semicolon.walletapi.domain.exception;


public class UserNameExistsException extends PiggyWalletException{
    public UserNameExistsException(String message) {
        super(message);
    }
}
