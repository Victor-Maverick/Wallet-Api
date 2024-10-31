package africa.semicolon.walletapi.domain.exception;

public class IncorrectPinException extends PiggyWalletException {
    public IncorrectPinException(String message) {
        super(message);
    }
}
