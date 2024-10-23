package africa.semicolon.walletapi.application.ports.input.paymentUseCases;

import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;

public interface InitializePaymentUseCase {
    InitializePaymentResponse initializePayment(InitializePaymentRequest request);
}
