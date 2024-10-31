package africa.semicolon.walletapi.application.ports.input.paymentUseCases.paystackUseCases;

import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.InitializeTransferResponse;

public interface InitializePaymentUseCase {
    InitializePaymentResponse initializePayment(InitializePaymentRequest request);

    InitializeTransferResponse initializeTransfer(InitializeTransferRequest request);
}
