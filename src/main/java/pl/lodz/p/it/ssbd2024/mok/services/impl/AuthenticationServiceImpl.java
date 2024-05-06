package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VerificationToken;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final OwnerRepository ownerRepository;
    private final AdministratorRepository administratorRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final VerificationTokenService verificationTokenService;

    @Value("${login_max_attempts:3}")
    private int maxLoginAttempts;

    @Value("${login_time_out:86400}")
    private int loginTimeOut;

    @Override
    public List<String> getUserRoles(User user) {
        List<String> roles = new ArrayList<>();

        ownerRepository.findByUserIdAndActive(user.getId(), true).ifPresent(owner -> roles.add("OWNER"));
        tenantRepository.findByUserIdAndActive(user.getId(), true).ifPresent(tenant -> roles.add("TENANT"));
        administratorRepository.findByUserIdAndActive(user.getId(), true).ifPresent(admin -> roles.add("ADMINISTRATOR"));

        return roles;
    }

    @Override
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

            return jwtService.generateToken(user.getId(), getUserRoles(user));
        } else {
            user.setLoginAttempts(user.getLoginAttempts() + 1);
            user.setLastFailedLogin(LocalDateTime.now());
            userRepository.saveAndFlush(user);

            if (user.getLoginAttempts() >= maxLoginAttempts) {
                LocalDateTime unblockDate = LocalDateTime.now().plusSeconds(loginTimeOut);
                emailService.sendLoginBlockEmail(user.getEmail(), user.getLoginAttempts(), user.getLastFailedLogin(), unblockDate, user.getLanguage());
            }
            throw new InvalidLoginDataException(UserExceptionMessages.INVALID_LOGIN_DATA);
        }
    }

    @Override
    public void verify(String token) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException {
        VerificationToken verificationToken = verificationTokenService.validateAccountVerificationToken(token);
        User user = userRepository.findById(verificationToken.getUser().getId()).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        user.setVerified(true);
        userRepository.saveAndFlush(user);
    }
}
