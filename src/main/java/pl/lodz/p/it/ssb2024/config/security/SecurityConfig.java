package pl.lodz.p.it.ssb2024.config.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import pl.lodz.p.it.ssb2024.mok.services.UserService;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/token").permitAll()
                                .requestMatchers("/authorized").hasAuthority("ROLE_user")
                                .requestMatchers("/owners/*/role").hasAuthority("ROLE_ADMINISTRATOR")
                                .requestMatchers("/admins/*/role").hasAuthority("ROLE_ADMINISTRATOR")
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(UserService userService) {
        byte[] bytes = jwtSecret.getBytes();
        SecretKey key = new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA512");
        NimbusJwtDecoder decoder = NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefault();
        OAuth2TokenValidator<Jwt> withOperation = new BlockedUserValidator(userService);
        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, withOperation));
        return decoder;
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        byte[] bytes = jwtSecret.getBytes();
        return new NimbusJwtEncoder(new ImmutableSecret<>(new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA512")));
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
