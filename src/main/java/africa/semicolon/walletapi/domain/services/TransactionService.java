package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.transactionUseCase.DeleteTransactionUseCase;
import africa.semicolon.walletapi.application.ports.input.transactionUseCase.GetAllTransactionsUseCase;
import africa.semicolon.walletapi.application.ports.input.transactionUseCase.GetTransactionUseCase;
import africa.semicolon.walletapi.application.ports.output.TransactionOutputPort;
import africa.semicolon.walletapi.domain.dtos.response.TransactionResponse;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.TransactionPersistenceMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TransactionService implements GetTransactionUseCase,GetAllTransactionsUseCase, DeleteTransactionUseCase {
    private final TransactionOutputPort transactionOutputPort;
    private final TransactionPersistenceMapper transactionPersistenceMapper;

    @Override
    public List<TransactionResponse> getTransactions(Long walletId) {
        List<TransactionResponse>transactions = transactionOutputPort.findAllTransactions(walletId);
        if (transactions.isEmpty()) return List.of();
        return transactions;
    }

    @Override
    public TransactionResponse getTransaction(Long transactionId) {
        return transactionOutputPort.getById(transactionId);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        transactionOutputPort.deleteTransaction(transactionId);
    }
}
