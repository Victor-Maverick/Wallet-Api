package africa.semicolon.walletapi.application.ports.input.transactionUseCase;

import africa.semicolon.walletapi.domain.dtos.response.TransactionResponse;

public interface GetTransactionUseCase {
    TransactionResponse getTransaction(Long transactionId);
}
