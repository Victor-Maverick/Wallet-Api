package africa.semicolon.walletapi.application.ports.input.userUseCases.identityVerificationUseCases;

import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;

import java.io.IOException;

public interface VerifyNinAndFaceUseCase {
    String verifyNinAndFace(VerificationRequest request) throws IOException;
}
