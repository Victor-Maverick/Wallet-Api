package africa.semicolon.walletapi.domain.exception;

public class InsufficientFundsApiException extends WalletApiException {
    public InsufficientFundsApiException(String message) {
        super(message);
    }
}
