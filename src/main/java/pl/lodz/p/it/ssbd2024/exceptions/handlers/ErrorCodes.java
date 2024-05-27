package pl.lodz.p.it.ssbd2024.exceptions.handlers;

public class ErrorCodes {
    public final static String OPTIMISTIC_LOCK = "optimisticLock";
    public final static String REGISTRATION_ERROR = "registrationError";
    public final static String IDENTICAL_LOGIN_OR_EMAIL = "identicalLoginOrEmail";
    public final static String INVALID_DATA = "invalidData";
    public final static String INVALID_LOGIN_DATA = "invalidLoginData";
    public final static String INVALID_PASSWORD = "invalidPassword";
    public final static String LOGIN_NOT_MATCH_TO_OTP = "loginNotMatchToOTP";
    public final static String PASSWORD_REPETITION = "passwordRepetition";
    public final static String INVALID_REFRESH_TOKEN = "invalidRefreshToken";
    public final static String SIGN_IN_BLOCKED = "signInBlocked";
    public final static String TIMEZONE_NOT_FOUND = "timezoneNotFound";
    public final static String USER_ALREADY_BLOCKED = "userAlreadyBlocked";
    public final static String USER_ALREADY_UNBLOCKED = "userAlreadyUnblocked";
    public final static String USER_BLOCKED = "userBlocked";
    public final static String USER_INACTIVE = "userInactive";
    public final static String USER_NOT_VERIFIED = "userNotVerified";
    public final static String VERIFICATION_TOKEN_EXPIRED = "verificationTokenExpired";
    public final static String VERIFICATION_TOKEN_USED = "verificationTokenUsed";
    public final static String INTERNAL_SERVER_ERROR = "internalServerError";
    public static final String ADMINISTRATOR_OWN_ROLE_REMOVAL = "administratorOwnRoleRemoval";
    public static final String ADMINISTRATOR_OWN_BLOCK = "administratorOwnBlock";
    public static final String NOT_FOUND = "notFound";
    public static final String SOMETHING_WENT_WRONG = "somethingWentWrong";
    public static final String ACCESS_DENIED = "accessDenied";
    public static final String JWT_TOKEN_INVALID = "jwtTokenInvalid";
    public static final String VALIDATION_ERROR = "validationError";
}
