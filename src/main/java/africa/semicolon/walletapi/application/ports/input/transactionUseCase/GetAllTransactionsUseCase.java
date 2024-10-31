package africa.semicolon.walletapi.application.ports.input.transactionUseCase;

import africa.semicolon.walletapi.domain.dtos.response.TransactionResponse;
import java.util.List;

public interface GetAllTransactionsUseCase {
    List<TransactionResponse> getTransactions(Long walletId);
}
