package pl.lodz.p.it.ssb2024.mok.services.impl;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Component
public class JwtService {
    private final JwtEncoder encoder;

    public JwtService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(UUID id, List<String> roles) {
        Instant now = Instant.now();
        StringBuilder authorities = new StringBuilder();
        for ( String role:
             roles) {
            authorities.append(" ").append(role);
        }
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("team-2.proj-sum.it.p.lodz.pl")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(id.toString())
                .claim("authorities",  authorities)
                .build();

        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return encoder.encode(encoderParameters).getTokenValue();
    }
}
