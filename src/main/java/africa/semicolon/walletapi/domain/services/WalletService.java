package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.walletUseCases.*;
import africa.semicolon.walletapi.application.ports.output.PaymentPort;
import africa.semicolon.walletapi.application.ports.output.TransactionOutputPort;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import africa.semicolon.walletapi.domain.exception.WalletNoFoundApiException;
import africa.semicolon.walletapi.domain.model.Transaction;
import africa.semicolon.walletapi.domain.model.Wallet;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static africa.semicolon.walletapi.domain.constants.TransactionType.CREDIT;

@AllArgsConstructor
@RequiredArgsConstructor
public class WalletService implements InitializeDepositUseCase,DepositUseCase, GetWalletUseCase {
    WalletOutputPort walletOutputPort;
    PaymentPort paymentPort;
    TransactionOutputPort transactionOutputPort;



    @Override
    @Transactional
    public VerifyPaymentResponse deposit(String reference, Long walletId) throws Exception {
        VerifyPaymentResponse verificationResponse = paymentPort.Verification(reference);
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
        return walletOutputPort.getWallet(id).orElseThrow(()->new WalletNoFoundApiException("Wallet not found with id "+id));
    }

    @Override
    public InitializePaymentResponse initializeDeposit(DepositRequest request) {
        InitializePaymentRequest paymentRequest = new InitializePaymentRequest();
        paymentRequest.setEmail(request.getEmail());
        paymentRequest.setAmount(request.getAmount());
        InitializePaymentResponse initializationResponse = paymentPort.initializePayment(paymentRequest);
        System.out.println("Click this link to verify: "+initializationResponse.getData().getAuthorizationUrl());
        return initializationResponse;
    }


}
