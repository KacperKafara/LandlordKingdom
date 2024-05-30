package pl.lodz.p.it.ssbd2024.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;

import java.util.Base64;
import java.util.Map;
import java.util.UUID;

public class Signer {

    private final String secretValue;

    public Signer(String secretValue) {
        this.secretValue = Base64.getEncoder().encodeToString(secretValue.getBytes());
    }

    public String generateSignature(UUID id, Long version) {
        try {
            Map<String, Object> claims = Map.of("id", id, "version", version);
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claims));
            JWSSigner signer = new MACSigner(secretValue);
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, OptimisticLockExceptionMessages.PROBLEM_WITH_ETAG_HEADER);
        }
    }
}
