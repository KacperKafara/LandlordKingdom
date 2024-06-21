package pl.lodz.p.it.ssbd2024.unit;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.*;
import pl.lodz.p.it.ssbd2024.mok.repositories.*;
import pl.lodz.p.it.ssbd2024.mok.services.impl.JwtService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;
import pl.lodz.p.it.ssbd2024.mol.repositories.*;
import pl.lodz.p.it.ssbd2024.util.Encoders;
import pl.lodz.p.it.ssbd2024.util.SignVerifier;

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

    @Bean
    OTPTokenRepository otpTokenRepository() {
        return Mockito.mock(OTPTokenRepository.class);
    }

    @Bean
    Encoders encoders() {
        return Mockito.mock(Encoders.class);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SignVerifier signVerifier() {
        return Mockito.mock(SignVerifier.class);
    }

    @Bean
    AuthUserRepository authUserRepository() {
        return Mockito.mock(AuthUserRepository.class);
    }

    @Bean
    AuthTenantRepository authTenantRepository() {
        return Mockito.mock(AuthTenantRepository.class);
    }

    @Bean
    AuthOwnerRepository authOwnerRepository() {
        return Mockito.mock(AuthOwnerRepository.class);
    }

    @Bean
    AuthAdministratorRepository authAdministratorRepository() {
        return Mockito.mock(AuthAdministratorRepository.class);
    }

    @Bean
    ThemeRepository themeRepository() {
        return Mockito.mock(ThemeRepository.class);
    }

    @Bean
    AccountActivateTokenRepository accountActivateTokenRepository() {
        return Mockito.mock(AccountActivateTokenRepository.class);
    }

    @Bean
    AddressRepository addressRepository() {
        return Mockito.mock(AddressRepository.class);
    }

    @Bean
    ApplicationRepository applicationRepository() {
        return Mockito.mock(ApplicationRepository.class);
    }

    @Bean
    FixedFeeRepository fixedFeeRepository() {
        return Mockito.mock(FixedFeeRepository.class);
    }

    @Bean
    LocalRepository localRepository() {
        return Mockito.mock(LocalRepository.class);
    }

    @Bean
    OwnerMolRepository ownerMolRepository() {
        return Mockito.mock(OwnerMolRepository.class);
    }

    @Bean
    PaymentRepository paymentRepository() {
        return Mockito.mock(PaymentRepository.class);
    }

    @Bean
    RentRepository rentRepository() {
        return Mockito.mock(RentRepository.class);
    }

    @Bean
    RoleRequestRepository roleRequestRepository() {
        return Mockito.mock(RoleRequestRepository.class);
    }

    @Bean
    TenantMolRepository tenantMolRepository() {
        return Mockito.mock(TenantMolRepository.class);
    }

    @Bean
    UserMolRepository userMolRepository() {
        return Mockito.mock(UserMolRepository.class);
    }

    @Bean
    VariableFeeRepository variableFeeRepository() {
        return Mockito.mock(VariableFeeRepository.class);
    }

    @Bean
    RoleRequestMOKRepository roleRequestMOKRepository() {
        return Mockito.mock(RoleRequestMOKRepository.class);
    }

    @Bean
    TimezoneRepository timezoneRepository() {
        return Mockito.mock(TimezoneRepository.class);
    }

    @Bean
    UserFilterRepository userFilterRepository() {
        return Mockito.mock(UserFilterRepository.class);
    }

    @Bean
    ImageRepository imageRepository() {
        return Mockito.mock(ImageRepository.class);
    }
}