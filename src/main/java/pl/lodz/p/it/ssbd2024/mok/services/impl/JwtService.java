package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final JwtEncoder refreshTokenEncoder;

    @Value("${refreshToken.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${refreshToken.expiration}")
    private long refreshTokenExpiration;

    public String generateToken(UUID id, List<String> roles) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("team-2.proj-sum.it.p.lodz.pl")
                .issuedAt(now)
                .expiresAt(now.plus(jwtExpiration, ChronoUnit.HOURS))
                .subject(id.toString())
                .claim("authorities",  roles)
                .build();

        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return jwtEncoder.encode(encoderParameters).getTokenValue();
    }

    public String generateRefreshToken(UUID id) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("team-2.proj-sum.it.p.lodz.pl")
                .issuedAt(now)
                .expiresAt(now.plus(refreshTokenExpiration, ChronoUnit.HOURS))
                .subject(id.toString())
                .build();

        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return refreshTokenEncoder.encode(encoderParameters).getTokenValue();
    }

    public boolean validateRefreshExpiration(Jwt refreshToken) {
        return Objects.requireNonNull(refreshToken.getExpiresAt()).isAfter(Instant.now());
    }

    public Jwt decodeRefreshToken(String token) {
        byte[] bytes = refreshTokenSecret.getBytes();
        SecretKey key = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA512");
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();

        return decoder.decode(token);
    }
}
