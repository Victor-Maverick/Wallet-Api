package africa.semicolon.walletapi.application.ports.input.paymentUseCases.paystackUseCases;

import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;

public interface VerifyPaymentUseCase {
    VerifyPaymentResponse Verification(String reference) throws Exception;
}
