package pl.lodz.p.it.ssbd2024.unit;

import jakarta.validation.constraints.Email;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import pl.lodz.p.it.ssbd2024.mok.repositories.*;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

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

    //TODO: mol repositories

}
