package africa.semicolon.walletapi.domain.exception;

public class UserNotFoundException extends PiggyWalletException{
    public UserNotFoundException(String message){
        super(message);
    }
}
