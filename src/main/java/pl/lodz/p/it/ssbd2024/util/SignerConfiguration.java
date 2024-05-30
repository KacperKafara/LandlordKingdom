package pl.lodz.p.it.ssbd2024.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignerConfiguration {

    @Value("${jws.secret}")
    private String secretValue;

    @Bean
    public Signer getSigner() {
        return new Signer(secretValue);
    }

    @Bean
    public SignVerifier getSignVerifier() {
        return new SignVerifier(secretValue);
    }
}
