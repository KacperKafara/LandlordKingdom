package pl.lodz.p.it.ssbd2024.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import jakarta.annotation.PostConstruct;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;

import java.text.ParseException;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

public class SignVerifier {

    private final String secretValue;

    public SignVerifier(String secretValue) {
        this.secretValue = Base64.getEncoder().encodeToString(secretValue.getBytes());
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
