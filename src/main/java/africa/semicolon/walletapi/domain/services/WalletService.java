package africa.semicolon.walletapi.domain.services;

import africa.semicolon.walletapi.application.ports.input.walletUseCases.*;
import africa.semicolon.walletapi.application.ports.output.WalletOutputPort;
import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.request.TransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import africa.semicolon.walletapi.domain.exception.IncorrectPinException;
import africa.semicolon.walletapi.domain.exception.InsufficientFundsException;
import africa.semicolon.walletapi.domain.exception.WalletNoFoundException;
import africa.semicolon.walletapi.domain.model.Wallet;


public class WalletService implements InitializeDepositUseCase,DepositUseCase, TransferUseCase, GetWalletUseCase, InitializeTransferUseCase {

    private final WalletOutputPort walletOutputPort;
    private final PaystackService paystackService;


    public WalletService(WalletOutputPort walletOutputPort, PaystackService paystackService) {
        this.paystackService = paystackService;
        this.walletOutputPort = walletOutputPort;
    }

    @Override
    public VerifyPaymentResponse deposit(String reference, Long walletId) throws Exception {
        VerifyPaymentResponse verificationResponse = paystackService.Verification(reference);
        Wallet wallet = getWallet(walletId);
        wallet.setBalance(wallet.getBalance().add(verificationResponse.getData().getAmount()));
        walletOutputPort.saveWallet(wallet);
        return verificationResponse;
    }

    @Override
    public Wallet getWallet(Long id) {
        return walletOutputPort.getWallet(id).orElseThrow(()->new WalletNoFoundException("Wallet not found with id "+id));
    }

    @Override
    public InitializePaymentResponse initializeDeposit(DepositRequest request) {
        InitializePaymentRequest paymentRequest = new InitializePaymentRequest();
        paymentRequest.setEmail(request.getEmail());
        paymentRequest.setAmount(request.getAmount().doubleValue());
        InitializePaymentResponse initializationResponse = paystackService.initializePayment(paymentRequest);
        System.out.println(initializationResponse.getData().getAuthorizationUrl());
        return initializationResponse;
    }

    @Override
    public InitializePaymentResponse initializeTransfer(InitializeTransferRequest request) {
        InitializePaymentRequest paymentRequest = new InitializePaymentRequest();
        paymentRequest.setEmail(request.getEmail());
        paymentRequest.setAmount(request.getAmount().doubleValue());
        InitializePaymentResponse initializationResponse = paystackService.initializePayment(paymentRequest);
        System.out.println(initializationResponse.getData().getAuthorizationUrl());
        return initializationResponse;
    }

    @Override
    public VerifyPaymentResponse transfer(String reference, TransferRequest transferRequest) {
        Wallet senderWallet = getWallet(transferRequest.getSenderWalletId());
        if (!senderWallet.getPin().equals(transferRequest.getPin()))throw new IncorrectPinException("invalid pin");

        Wallet receiverWallet = getWallet(transferRequest.getReceiverWalletId());
        try {
            VerifyPaymentResponse verificationResponse = paystackService.Verification(reference);
            if (senderWallet.getBalance().compareTo(verificationResponse.getData().getAmount()) < 0)
                throw new InsufficientFundsException("insufficient funds");
            senderWallet.setBalance(senderWallet.getBalance().subtract(verificationResponse.getData().getAmount()));
            walletOutputPort.saveWallet(senderWallet);
            receiverWallet.setBalance(receiverWallet.getBalance().add(verificationResponse.getData().getAmount()));
            walletOutputPort.saveWallet(receiverWallet);
            return verificationResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
