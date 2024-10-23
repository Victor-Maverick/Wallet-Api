package africa.semicolon.walletapi.application.ports.input.walletUseCases;

import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;

public interface InitializeTransferUseCase {
    InitializePaymentResponse initializeTransfer(InitializeTransferRequest request);
}
