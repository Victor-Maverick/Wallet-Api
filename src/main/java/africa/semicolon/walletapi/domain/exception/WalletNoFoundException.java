package africa.semicolon.walletapi.domain.exception;

public class WalletNoFoundException extends PiggyWalletException {
    public WalletNoFoundException(String message) {
        super(message);
    }
}
