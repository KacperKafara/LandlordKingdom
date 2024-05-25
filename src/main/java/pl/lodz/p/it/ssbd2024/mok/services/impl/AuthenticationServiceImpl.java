package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.exceptions.VerificationTokenUsedException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.VerificationToken;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthAdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthOwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthTenantRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthUserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;

import java.security.InvalidKeyException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;

    private final AuthUserRepository userRepository;
    private final AuthTenantRepository tenantRepository;
    private final AuthOwnerRepository ownerRepository;
    private final AuthAdministratorRepository administratorRepository;

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private final VerificationTokenService verificationTokenService;

    @Value("${login.maxAttempts}")
    private int maxLoginAttempts;

    @Value("${login.timeOut}")
    private int loginTimeOut;

    @Transactional(propagation = Propagation.MANDATORY)
    @PreAuthorize("permitAll()")
    public List<String> getUserRoles(User user) {
        List<String> roles = new ArrayList<>();

        ownerRepository.findByUserIdAndActive(user.getId(), true).ifPresent(owner -> roles.add("OWNER"));
        tenantRepository.findByUserIdAndActive(user.getId(), true).ifPresent(tenant -> roles.add("TENANT"));
        administratorRepository.findByUserIdAndActive(user.getId(), true).ifPresent(admin -> roles.add("ADMINISTRATOR"));

        return roles;
    }

    @Override
    @PreAuthorize("permitAll()")
    public void generateOTP(String login, String password, String language, String ip) throws InvalidKeyException, NotFoundException, UserNotVerifiedException, UserBlockedException, SignInBlockedException, InvalidLoginDataException {
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
            user.setLanguage(language);
            userRepository.saveAndFlush(user);

            String token = verificationTokenService.generateOTPToken(user);
            emailService.sendOTPEmail(user.getEmail(), user.getFirstName(), token, user.getLanguage());
        } else {
            handleFailedLogin(user, ip);
            throw new InvalidLoginDataException(UserExceptionMessages.INVALID_LOGIN_DATA);
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> verifyOTP(String token, String login, String ip) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException, LoginNotMatchToOTPException {
        VerificationToken verificationToken;

        try {
            verificationToken = verificationTokenService.validateOTPToken(token);
        } catch (VerificationTokenUsedException e) {
            User user = userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
            handleFailedLogin(user, ip);
            throw e;
        }

        User user = verificationToken.getUser();

        if (!user.getLogin().equals(login)) {
            handleFailedLogin(user, ip);
            throw new LoginNotMatchToOTPException(UserExceptionMessages.LOGIN_NOT_MATCH_TO_OTP);
        }

        user.setLastSuccessfulLogin(LocalDateTime.now());
        user.setLoginAttempts(0);
        user.setLastSuccessfulLoginIp(ip);

        List<String> roles = getUserRoles(user);

        if (roles.contains("ADMINISTRATOR")) {
            emailService.sendAdminLoginEmail(user.getEmail(), user.getFirstName(), ip, user.getLanguage());
        }

        String jwt = jwtService.generateToken(verificationToken.getUser().getId(), getUserRoles(verificationToken.getUser()));
        String refreshToken = jwtService.generateRefreshToken(verificationToken.getUser().getId());
        String theme = user.getTheme().name().toLowerCase();

        return Map.of(
                "token", jwt,
                "refreshToken", refreshToken,
                "theme", theme);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @PreAuthorize("permitAll()")
    protected void handleFailedLogin(User user, String ip) {
        user.setLastFailedLogin(LocalDateTime.now());
        user.setLastFailedLoginIp(ip);
        user.setLoginAttempts(user.getLoginAttempts() + 1);
        userRepository.saveAndFlush(user);

        if (user.getLoginAttempts() >= maxLoginAttempts) {
            LocalDateTime unblockDate = LocalDateTime.now().plusSeconds(loginTimeOut);
            emailService.sendLoginBlockEmail(user.getEmail(), user.getLoginAttempts(), user.getLastFailedLogin(), unblockDate, user.getLastFailedLoginIp(), user.getLanguage());
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> refresh(String refreshToken) throws NotFoundException, RefreshTokenExpiredException {
        Jwt token = jwtService.decodeRefreshToken(refreshToken);

        UUID userId = UUID.fromString(token.getSubject());
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        if (jwtService.validateRefreshExpiration(token)) {
            return Map.of(
                    "token", jwtService.generateToken(userId, getUserRoles(user)),
                    "refreshToken", refreshToken);
        }

        throw new RefreshTokenExpiredException(UserExceptionMessages.REFRESH_TOKEN_EXPIRED);
    }
}
