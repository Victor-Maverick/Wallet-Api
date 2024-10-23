package africa.semicolon.walletapi.domain.exception;

public class WalletNoFoundException extends RuntimeException {
    public WalletNoFoundException(String message) {
        super(message);
    }
}
