package africa.semicolon.walletapi.infrastructure.adapter;

import africa.semicolon.walletapi.application.ports.output.TransactionOutputPort;
import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.infrastructure.adapter.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionOutputPort {
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction save(Transaction transaction) {
        return null;
    }

    @Override
    public void delete(Transaction transaction) {

    }
}
