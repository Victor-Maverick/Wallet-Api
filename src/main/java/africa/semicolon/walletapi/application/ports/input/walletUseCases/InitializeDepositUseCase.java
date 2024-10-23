package africa.semicolon.walletapi.application.ports.input.walletUseCases;

import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;

public interface InitializeDepositUseCase {
    InitializePaymentResponse initializeDeposit(DepositRequest request);
}
