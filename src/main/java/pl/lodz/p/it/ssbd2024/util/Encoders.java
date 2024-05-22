package pl.lodz.p.it.ssbd2024.util;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.UUID;

@Configuration
public class Encoders {
    private PrivateKey privateJwtKey;
    private PublicKey publicJwtKey;
    private PrivateKey privateRefreshTokenKey;
    private PublicKey publicRefreshTokenKey;

    @Value("${jwt.privateKeyFilePath}")
    private String privateJwtKeyFilePath;
    @Value("${refreshToken.privateKeyFilePath}")
    private String privateRefreshTokenKeyFilePath;
    @Value("${jwt.publicKeyFilePath}")
    private String publicJwtKeyFilePath;
    @Value("${refreshToken.publicKeyFilePath}")
    private String publicRefreshTokenKeyFilePath;

    @PostConstruct
    public void readKeys() throws IOException {
        this.privateJwtKey = KeyReader.readPrivateJwtKey(privateJwtKeyFilePath);
        this.privateRefreshTokenKey = KeyReader.readPrivateJwtKey(privateRefreshTokenKeyFilePath);
        this.publicJwtKey = KeyReader.readPublicJwtKey(publicJwtKeyFilePath);
        this.publicRefreshTokenKey = KeyReader.readPublicJwtKey(publicRefreshTokenKeyFilePath);
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder((RSAPublicKey) publicJwtKey)
                .privateKey(privateJwtKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtEncoder refreshTokenEncoder() {
        JWK jwk = new RSAKey.Builder((RSAPublicKey) publicRefreshTokenKey)
                .privateKey(privateRefreshTokenKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
