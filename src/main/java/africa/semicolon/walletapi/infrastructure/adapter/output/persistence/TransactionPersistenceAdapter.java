package africa.semicolon.walletapi.infrastructure.adapter.output.persistence;

import africa.semicolon.walletapi.application.ports.output.TransactionOutputPort;
import africa.semicolon.walletapi.application.ports.output.UserOutputPort;
import africa.semicolon.walletapi.domain.dtos.response.TransactionResponse;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.domain.model.User;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.TransactionEntity;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.entities.UserEntity;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.TransactionPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.mapper.UserPersistenceMapper;
import africa.semicolon.walletapi.infrastructure.adapter.output.persistence.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionOutputPort {
    private static final Logger log = LoggerFactory.getLogger(TransactionPersistenceAdapter.class);
    private final TransactionRepository transactionRepository;
    private final TransactionPersistenceMapper transactionPersistenceMapper;
    private final UserOutputPort userOutputPort;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity transactionEntity = transactionPersistenceMapper.toTransactionEntity(transaction);
        transactionRepository.save(transactionEntity);
        return transactionPersistenceMapper.toTransaction(transactionEntity);
    }

    @Override
    public TransactionResponse getById(Long id) {
        final TransactionEntity transactionEntity = transactionRepository.findById(id)
                .orElseThrow(()-> new PiggyWalletException("transaction not found"));
        return new TransactionResponse(transactionEntity);
    }

    @Override
    public List<TransactionResponse> findAllTransactions(Long userId) {
        User user = userOutputPort.getById(userId);
        List<TransactionEntity> transactions = transactionRepository.findAllForWallet(user.getWallet().getWalletId());
        return transactions
                .stream().map(TransactionResponse::new)
                .toList();
    }


    @Override
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

}
