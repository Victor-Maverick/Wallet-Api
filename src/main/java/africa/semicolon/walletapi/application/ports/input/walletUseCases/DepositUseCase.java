package africa.semicolon.walletapi.application.ports.input.walletUseCases;

import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;

public interface DepositUseCase {
    VerifyPaymentResponse deposit(String reference, Long walletId) throws Exception;
}
