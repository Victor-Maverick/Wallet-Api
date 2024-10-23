package africa.semicolon.walletapi.application.ports.input.walletUseCases;

import africa.semicolon.walletapi.domain.dtos.request.TransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;

public interface TransferUseCase {
    VerifyPaymentResponse transfer(String reference, TransferRequest transferRequest);

}
