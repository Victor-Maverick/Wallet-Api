package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.dtos.response.TransactionResponse;
import africa.semicolon.walletapi.domain.model.Transaction;

import java.util.List;

public interface TransactionOutputPort {
    Transaction save(Transaction transaction);
    void deleteTransaction(Long transactionId);
    TransactionResponse getById(Long transactionId);
    List<TransactionResponse> findAllTransactions(Long walletId);
}
