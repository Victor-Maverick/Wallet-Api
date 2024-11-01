package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.walletUseCases.*;
import africa.semicolon.walletapi.application.ports.output.TransactionOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.request.TransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import africa.semicolon.walletapi.domain.exception.IncorrectPinException;
import africa.semicolon.walletapi.domain.exception.InsufficientFundsException;
import africa.semicolon.walletapi.domain.exception.WalletNoFoundException;
import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.domain.model.Wallet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static africa.semicolon.walletapi.domain.constants.TransactionType.CREDIT;

@AllArgsConstructor
public class WalletService implements InitializeDepositUseCase,DepositUseCase, TransferUseCase, GetWalletUseCase {
    private final WalletOutputPort walletOutputPort;
    private final PaystackService paystackService;
    private final TransactionOutputPort transactionOutputPort;



    @Override
    @Transactional
    public VerifyPaymentResponse deposit(String reference, Long walletId) throws Exception {
        VerifyPaymentResponse verificationResponse = paystackService.Verification(reference);
        Wallet wallet = getWallet(walletId);

        BigDecimal amount = BigDecimal.valueOf(verificationResponse.getData().getAmount());
        wallet.setBalance(wallet.getBalance().add(amount));

        wallet = walletOutputPort.saveWallet(wallet);
        buildAndSaveTransaction(wallet, amount);

        return verificationResponse;
    }

    private void buildAndSaveTransaction(Wallet wallet, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setType(CREDIT);
        transaction.setAmount(amount);
        transaction.setWallet(wallet);
        transactionOutputPort.save(transaction);
    }


    @Override
    public Wallet getWallet(Long id) {
        return walletOutputPort.getWallet(id).orElseThrow(()->new WalletNoFoundException("Wallet not found with id "+id));
    }

    @Override
    public InitializePaymentResponse initializeDeposit(DepositRequest request) {
        InitializePaymentRequest paymentRequest = new InitializePaymentRequest();
        paymentRequest.setEmail(request.getEmail());
        paymentRequest.setAmount(request.getAmount());
        InitializePaymentResponse initializationResponse = paystackService.initializePayment(paymentRequest);
        System.out.println("Click this link to verify: "+initializationResponse.getData().getAuthorizationUrl());
        return initializationResponse;
    }



    @Override
    public VerifyPaymentResponse transfer(String reference, TransferRequest transferRequest) {
        Wallet senderWallet = getWallet(transferRequest.getSenderWalletId());
        if (!senderWallet.getPin().equals(transferRequest.getPin()))throw new IncorrectPinException("invalid pin");

        Wallet receiverWallet = getWallet(transferRequest.getReceiverWalletId());
        try {
            VerifyPaymentResponse verificationResponse = paystackService.Verification(reference);
            if (senderWallet.getBalance().compareTo(BigDecimal.valueOf(verificationResponse.getData().getAmount())) < 0)
                throw new InsufficientFundsException("insufficient funds");
            senderWallet.setBalance(senderWallet.getBalance().subtract(BigDecimal.valueOf(verificationResponse.getData().getAmount())));
            walletOutputPort.saveWallet(senderWallet);
            receiverWallet.setBalance(receiverWallet.getBalance().add(BigDecimal.valueOf(verificationResponse.getData().getAmount())));
            walletOutputPort.saveWallet(receiverWallet);
            return verificationResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
