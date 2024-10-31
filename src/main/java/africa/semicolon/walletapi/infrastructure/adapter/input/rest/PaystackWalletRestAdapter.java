package africa.semicolon.walletapi.infrastructure.adapter.input.rest;

import africa.semicolon.walletapi.application.ports.input.transactionUseCase.DeleteTransactionUseCase;
import africa.semicolon.walletapi.application.ports.input.transactionUseCase.GetAllTransactionsUseCase;
import africa.semicolon.walletapi.application.ports.input.transactionUseCase.GetTransactionUseCase;
import africa.semicolon.walletapi.application.ports.input.walletUseCases.*;
import africa.semicolon.walletapi.domain.dtos.request.DepositRequest;
import africa.semicolon.walletapi.domain.dtos.response.InitializePaymentResponse;
import africa.semicolon.walletapi.domain.dtos.response.TransactionResponse;
import africa.semicolon.walletapi.domain.dtos.response.VerifyPaymentResponse;
import africa.semicolon.walletapi.domain.exception.PiggyWalletException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/wallets")
@RequiredArgsConstructor
public class PaystackWalletRestAdapter {
    private final InitializeDepositUseCase initializeDepositUseCase;
    private final DepositUseCase depositUseCase;
    private final GetAllTransactionsUseCase getAllTransactionsUseCase;
    private final GetTransactionUseCase getTransactionUseCase;
    private final DeleteTransactionUseCase deleteTransactionUseCase;

    @PostMapping("/initiateDeposit")
    public ResponseEntity<?> initiateDeposit(@RequestBody @Valid DepositRequest depositRequest) {
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

    @GetMapping("/getAllTransactions{walletId}")
    public ResponseEntity<?> getAllTransactions(@PathVariable Long walletId) {
        try{
            List<TransactionResponse> response = getAllTransactionsUseCase.getTransactions(walletId);
            return new ResponseEntity<>(response,OK);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }
    @GetMapping("/getTransaction/{id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long id) {
        try{
            TransactionResponse response = getTransactionUseCase.getTransaction(id);
            return new ResponseEntity<>(response,OK);
        }
        catch (PiggyWalletException exception) {
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteTransaction{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        try{
            deleteTransactionUseCase.deleteTransaction(id);
            return ResponseEntity.ok().build();
        }
        catch (PiggyWalletException exception){
            return new ResponseEntity<>(exception.getMessage(),BAD_REQUEST);
        }
    }

}
