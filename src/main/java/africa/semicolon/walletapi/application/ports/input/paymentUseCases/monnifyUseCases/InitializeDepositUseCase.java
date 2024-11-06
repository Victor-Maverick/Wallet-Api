package africa.semicolon.walletapi.application.ports.input.paymentUseCases.monnifyUseCases;

import africa.semicolon.walletapi.domain.dtos.request.InitiateMonnifyTransactionRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitiateMonnifyTransactionResponse;

public interface InitializeDepositUseCase {
    InitiateMonnifyTransactionResponse initializeMonnifyTransaction(InitiateMonnifyTransactionRequest request);
}
