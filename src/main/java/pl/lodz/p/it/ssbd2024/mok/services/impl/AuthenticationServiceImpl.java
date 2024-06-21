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
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.model.tokens.VerificationToken;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthAdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthOwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthTenantRepository;
import pl.lodz.p.it.ssbd2024.mok.authRepositories.AuthUserRepository;
import pl.lodz.p.it.ssbd2024.mok.dto.PasswordHolder;
import pl.lodz.p.it.ssbd2024.mok.dto.oauth.GoogleOAuth2TokenPayload;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;
import pl.lodz.p.it.ssbd2024.util.DateUtils;

import java.security.InvalidKeyException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthUserRepository userRepository;
    private final AuthTenantRepository tenantRepository;
    private final AuthOwnerRepository ownerRepository;
    private final AuthAdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final VerificationTokenService verificationTokenService;
    private final UserService userService;

    @Value("${login.maxAttempts:3}")
    private int maxLoginAttempts;

    @Value("${login.timeOut:86400}")
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
    public void generateOTP(String login, PasswordHolder password, String language, String ip) throws InvalidKeyException, NotFoundException, UserNotVerifiedException, UserBlockedException, SignInBlockedException, InvalidLoginDataException, TokenGenerationException, UserInactiveException {
        User user = userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (!user.isVerified()) {
            throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED, ErrorCodes.USER_NOT_VERIFIED);
        }

        if (user.isBlocked()) {
            throw new UserBlockedException(UserExceptionMessages.BLOCKED, ErrorCodes.USER_BLOCKED);
        }

        if (user.getLoginAttempts() >= maxLoginAttempts && Duration.between(user.getLastFailedLogin(), LocalDateTime.now()).toSeconds() <= loginTimeOut) {
            throw new SignInBlockedException(UserExceptionMessages.SIGN_IN_BLOCKED, ErrorCodes.SIGN_IN_BLOCKED);
        } else if (user.getLoginAttempts() >= maxLoginAttempts) {
            user.setLoginAttempts(0);
        }

        if (passwordEncoder.matches(password.password(), user.getPassword())) {
            if (!user.isActive()) {
                String token = verificationTokenService.generateAccountActivateToken(user);
                emailService.sendAccountActivateAfterBlock(user.getEmail(), user.getFirstName(), token, language);
                throw new UserInactiveException(UserExceptionMessages.INACTIVE, ErrorCodes.USER_INACTIVE);
            }

            user.setLanguage(language);
            userRepository.saveAndFlush(user);

            String token = verificationTokenService.generateOTPToken(user);
            emailService.sendOTPEmail(user.getEmail(), user.getFirstName(), token, user.getLanguage());
        } else {
            handleFailedLogin(user, ip);
            throw new InvalidLoginDataException(UserExceptionMessages.INVALID_LOGIN_DATA, ErrorCodes.INVALID_LOGIN_DATA);
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> verifyOTP(String token, String login, String ip) throws VerificationTokenUsedException, VerificationTokenExpiredException, NotFoundException, LoginNotMatchToOTPException {
        VerificationToken verificationToken;

        try {
            verificationToken = verificationTokenService.validateOTPToken(token);
        } catch (VerificationTokenUsedException e) {
            User user = userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));
            handleFailedLogin(user, ip);
            throw e;
        }

        User user = verificationToken.getUser();

        if (!user.getLogin().equals(login)) {
            handleFailedLogin(user, ip);
            throw new LoginNotMatchToOTPException(UserExceptionMessages.LOGIN_NOT_MATCH_TO_OTP, ErrorCodes.LOGIN_NOT_MATCH_TO_OTP);
        }

        user.setLastSuccessfulLogin(LocalDateTime.now());
        user.setLoginAttempts(0);
        user.setLastSuccessfulLoginIp(ip);

        List<String> roles = getUserRoles(user);

        if (roles.contains("ADMINISTRATOR")) {
            emailService.sendAdminLoginEmail(user.getEmail(), user.getFirstName(), ip, user.getLanguage());
        }

        String jwt = jwtService.generateToken(verificationToken.getUser().getId(), verificationToken.getUser().getLogin(), getUserRoles(verificationToken.getUser()));
        String refreshToken = jwtService.generateRefreshToken(verificationToken.getUser().getId());
        String theme = user.getTheme() != null ?
                user.getTheme().getType().toLowerCase() : "light";

        log.info("Session started for user: {} - {}, from address IP: {}", login, user.getId(), ip);
        return Map.of(
                "token", jwt,
                "refreshToken", refreshToken,
                "theme", theme);
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> singInOAuth(String token, String ip, GoogleOAuth2TokenPayload payload) throws UserNotVerifiedException, TokenGenerationException, CreationException, IdenticalFieldValueException {
        try {
            User user = userService.getUserByGoogleId(payload.getSub());

            if (!user.isVerified()) {
                throw new UserNotVerifiedException(UserExceptionMessages.NOT_VERIFIED, ErrorCodes.USER_NOT_VERIFIED);
            }

            List<String> roles = userService.getUserRoles(user.getId());
            String userToken = jwtService.generateToken(user.getId(), user.getLogin(), roles);
            String refreshToken = jwtService.generateRefreshToken(user.getId());
            String theme = user.getTheme() != null ?
                    user.getTheme().getType().toLowerCase() : "light";


            return Map.of(
                    "token", userToken,
                    "refreshToken", refreshToken,
                    "theme", theme);

        } catch (NotFoundException e) {
            User newUser = new User(
                    payload.getGivenName(),
                    payload.getFamilyName(),
                    payload.getEmail(),
                    payload.getEmail(),
                    "en"
            );
            newUser.setGoogleId(payload.getSub());
            userService.createUser(newUser);
            return Map.of(
                    "created", "true"
            );
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @PreAuthorize("permitAll()")
    protected void handleFailedLogin(User user, String ip) {
        user.setLastFailedLogin(LocalDateTime.now());
        user.setLastFailedLoginIp(ip);
        user.setLoginAttempts(user.getLoginAttempts() + 1);
        userRepository.saveAndFlush(user);

        if (user.getLoginAttempts() >= maxLoginAttempts) {
            String timezone = "UTC";
            if (user.getTimezone() != null) {
                timezone = user.getTimezone().getName();
            }
            String unblockDate = DateUtils.convertUTCToAnotherTimezoneSimple(LocalDateTime.now().plusSeconds(loginTimeOut), timezone, user.getLanguage());
            String lastFailedLogin = DateUtils.convertUTCToAnotherTimezoneSimple(user.getLastFailedLogin(), timezone, user.getLanguage());
            emailService.sendLoginBlockEmail(user.getEmail(), user.getLoginAttempts(), lastFailedLogin, unblockDate, user.getLastFailedLoginIp(), user.getLanguage());
        }
    }

    @Override
    @PreAuthorize("permitAll()")
    public Map<String, String> refresh(String refreshToken) throws NotFoundException, RefreshTokenExpiredException {
        Jwt token = jwtService.decodeRefreshToken(refreshToken);

        UUID userId = UUID.fromString(token.getSubject());
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND, ErrorCodes.USER_NOT_FOUND));

        if (jwtService.validateRefreshExpiration(token)) {
            return Map.of(
                    "token", jwtService.generateToken(userId, user.getLogin(), getUserRoles(user)),
                    "refreshToken", refreshToken);
        }

        throw new RefreshTokenExpiredException(UserExceptionMessages.REFRESH_TOKEN_EXPIRED, ErrorCodes.INVALID_REFRESH_TOKEN);
    }
}
