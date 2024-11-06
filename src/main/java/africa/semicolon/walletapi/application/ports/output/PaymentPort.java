package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.dtos.request.InitializePaymentRequest;
import africa.semicolon.walletapi.domain.dtos.request.InitializeTransferRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.InitializeTransferResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;

public interface PaymentPort {
    InitializePaymentResponse initializePayment(InitializePaymentRequest request);
    VerifyPaymentResponse Verification(String reference) throws Exception;
    InitializeTransferResponse initializeTransfer(InitializeTransferRequest request);

}
