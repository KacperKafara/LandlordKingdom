package pl.lodz.p.it.ssbd2024.unit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import pl.lodz.p.it.ssbd2024.mok.repositories.*;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;
import pl.lodz.p.it.ssbd2024.services.EmailService;
import pl.lodz.p.it.ssbd2024.util.Encoders;
import pl.lodz.p.it.ssbd2024.util.SignVerifier;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("unit")
public class MockConfig {
    @Bean
    JavaMailSender javaMailSender() {
        return Mockito.mock(JavaMailSender.class);
    }

    @Bean
    EmailService emailService() {
        return Mockito.mock(EmailService.class);
    }

    @Bean
    SpringTemplateEngine templateEngine() {
        return Mockito.mock(SpringTemplateEngine.class);
    }

    @Bean
    ITemplateResolver templateResolver() {
        return Mockito.mock(ITemplateResolver.class);
    }

    @Bean
    JwtService jwtService() {
        return Mockito.mock(JwtService.class);
    }

    @Bean
    UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

    @Bean
    AdministratorRepository administratorRepository() {
        return Mockito.mock(AdministratorRepository.class);
    }

    @Bean
    OwnerRepository ownerRepository() {
        return Mockito.mock(OwnerRepository.class);
    }

    @Bean
    TenantRepository tenantRepository() {
        return Mockito.mock(TenantRepository.class);
    }

    @Bean
    AccountVerificationTokenRepository accountVerificationTokenRepository() {
        return Mockito.mock(AccountVerificationTokenRepository.class);
    }

    @Bean
    EmailVerificationTokenRepository emailVerificationTokenRepository() {
        return Mockito.mock(EmailVerificationTokenRepository.class);
    }

    @Bean
    PasswordVerificationTokenRepository passwordVerificationTokenRepository() {
        return Mockito.mock(PasswordVerificationTokenRepository.class);
    }

//    @Bean
//    VerificationTokenService verificationTokenService() {
//        return Mockito.mock(VerificationTokenService.class);
//    }

    @Bean
    OTPTokenRepository otpTokenRepository() {
        return Mockito.mock(OTPTokenRepository.class);
    }

    @Bean
    SignVerifier signVerifier() {
        return Mockito.mock(SignVerifier.class);
    }

    @Bean
    Encoders encoders() {
        return Mockito.mock(Encoders.class);
    }

    //TODO: mol repositories

}
