package pl.lodz.p.it.ssbd2024.unit;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
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

    //TODO: mol repositories

}
