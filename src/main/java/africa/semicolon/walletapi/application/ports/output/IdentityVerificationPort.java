package africa.semicolon.walletapi.application.ports.output;

import africa.semicolon.walletapi.domain.dtos.request.VerificationRequest;

import java.io.IOException;

public interface IdentityVerificationPort {
    String verifyNinAndFace(VerificationRequest request) throws IOException;
}
