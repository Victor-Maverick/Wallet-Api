package africa.semicolon.walletapi.domain.exception;

public class InsufficientFundsException extends PiggyWalletException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
