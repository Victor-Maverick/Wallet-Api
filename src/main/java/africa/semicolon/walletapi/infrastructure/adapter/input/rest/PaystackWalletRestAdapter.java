package africa.semicolon.walletapi.infrastructure.adapter.input.rest;

import africa.semicolon.walletapi.application.ports.input.walletUseCases.*;
import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class WalletRestAdapter {
    private final InitializeDepositUseCase initializeDepositUseCase;
    private final DepositUseCase depositUseCase;
    private final InitializeTransferUseCase initializeTransferUseCase;
    private final TransferUseCase transferUseCase;

    @PostMapping("/initiateDeposit")
    public ResponseEntity<?> initiateDeposit(@RequestBody DepositRequest depositRequest) {
        InitializePaymentResponse response = initializeDepositUseCase.initializeDeposit(depositRequest);
        return new ResponseEntity<>(response,OK);
    }

    @PostMapping("/verifyDeposit/{reference}/{walletId}")
    public ResponseEntity<?> verifyDeposit(@PathVariable String reference, @PathVariable Long walletId) {
        try {
            VerifyPaymentResponse response = depositUseCase.deposit(reference, walletId);
            return new ResponseEntity<>(response,OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }

}
