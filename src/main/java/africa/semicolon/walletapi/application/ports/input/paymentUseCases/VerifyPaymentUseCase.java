package africa.semicolon.walletapi.application.ports.input.paymentUseCases;

import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;

public interface VerifyPaymentUseCase {
    VerifyPaymentResponse Verification(String reference) throws Exception;
}
