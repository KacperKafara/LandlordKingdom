package pl.lodz.p.it.ssbd2024.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;

import java.text.ParseException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

@Component
public class Signer {

    @Value("${jws.secret}")
    String secretValue;

    @PostConstruct
    public void init() {
        secretValue = Base64.getEncoder().encodeToString(secretValue.getBytes());
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

    private boolean verifySignature(String token) throws ParseException, JOSEException {
        JWSObject jwsObject = JWSObject.parse(token);
        JWSVerifier verifier = new MACVerifier(secretValue);
        return jwsObject.verify(verifier);
    }

    public boolean verifySignature(UUID id, Long version, String token) {
        try {
            if (!verifySignature(token)) return false;
            JWSObject jwsObject = JWSObject.parse(token);
            Map<String, Object> claims = jwsObject.getPayload().toJSONObject();
            return id.equals(UUID.fromString((String) claims.get("id"))) && version.equals(claims.get("version"));
        } catch (ParseException | JOSEException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, OptimisticLockExceptionMessages.PROBLEM_WITH_IF_MATCH_HEADER);
        }
    }

}
