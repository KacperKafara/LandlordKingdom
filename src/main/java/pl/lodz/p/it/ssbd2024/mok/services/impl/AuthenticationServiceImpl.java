package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.aspects.TxTracked;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Log
@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final OwnerRepository ownerRepository;
    private final AdministratorRepository administratorRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${login_max_attempts:3}")
    private int maxLoginAttempts;

    @Value("${login_time_out:86400}")
    private int loginTimeOut;

    @Autowired
    public AuthenticationServiceImpl(JwtService jwtService, OwnerRepository ownerRepository, TenantRepository tenantRepository, AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, EmailService emailService) {
        this.jwtService = jwtService;
        this.ownerRepository = ownerRepository;
        this.tenantRepository = tenantRepository;
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }


    @Override
    public List<String> getUserRoles(User user) {
        List<String> roles = new ArrayList<>();

        ownerRepository.findByUserIdAndActive(user.getId(), true).ifPresent(owner -> roles.add("OWNER"));
        tenantRepository.findByUserIdAndActive(user.getId(), true).ifPresent(tenant -> roles.add("TENANT"));
        administratorRepository.findByUserIdAndActive(user.getId(), true).ifPresent(admin -> roles.add("ADMINISTRATOR"));

        return roles;
    }

    @Override
    @Transactional
    public String authenticate(String login, String password) throws NotFoundException, UserNotVerifiedException, UserBlockedException, InvalidLoginDataException, SignInBlockedException {
        log.info("test");
        User user = userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (!user.isVerified()) {
            throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED);
        }

        if (user.isBlocked()) {
            throw new UserBlockedException(UserExceptionMessages.BLOCKED);
        }

        if (user.getLoginAttempts() >= maxLoginAttempts && Duration.between(user.getLastFailedLogin(), LocalDateTime.now()).toSeconds() <= loginTimeOut) {
            throw new SignInBlockedException(UserExceptionMessages.SIGN_IN_BLOCKED);
        } else if (user.getLoginAttempts() >= maxLoginAttempts) {
            user.setLoginAttempts(0);
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            user.setLastSuccessfulLogin(LocalDateTime.now());
            user.setLoginAttempts(0);
            userRepository.saveAndFlush(user);

            String jwtToken = jwtService.generateToken(user.getId(), getUserRoles(user));
            return jwtToken;
        } else {
            if (user.getLoginAttempts() + 1 >= maxLoginAttempts) {
                emailService.sendEmail(user.getEmail(), "Your account has been blocked", "You reached max login attempts so your account is blocked.");
            }
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            user.setLastFailedLogin(LocalDateTime.now());
            userRepository.saveAndFlush(user);

            throw new InvalidLoginDataException(UserExceptionMessages.INVALID_LOGIN_DATA);
        }
    }
}
