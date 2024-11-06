package africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases;

import africa.semicolon.walletapi.domain.dtos.request.MonnifyTransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.MonnifyTransferResponse;

public interface InitiateTransferUseCase {
    MonnifyTransferResponse initiateTransfer(MonnifyTransferRequest request);
}
