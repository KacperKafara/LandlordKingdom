package pl.lodz.p.it.ssbd2024.exceptions.handlers;

public class ErrorCodes {


    private ErrorCodes() {
    }

    public static final String OPTIMISTIC_LOCK = "optimisticLock";
    public static final String REGISTRATION_ERROR = "registrationError";
    public static final String IDENTICAL_LOGIN_OR_EMAIL = "identicalLoginOrEmail";
    public static final String IDENTICAL_EMAIL = "identicalEmail";
    public static final String INVALID_DATA = "invalidData";
    public static final String INVALID_LOGIN_DATA = "invalidLoginData";
    public static final String INVALID_PASSWORD = "invalidPassword";
    public static final String LOGIN_NOT_MATCH_TO_OTP = "loginNotMatchToOTP";
    public static final String PASSWORD_REPETITION = "passwordRepetition";
    public static final String INVALID_REFRESH_TOKEN = "invalidRefreshToken";
    public static final String SIGN_IN_BLOCKED = "signInBlocked";
    public static final String USER_ALREADY_BLOCKED = "userAlreadyBlocked";
    public static final String USER_ALREADY_UNBLOCKED = "userAlreadyUnblocked";
    public static final String USER_BLOCKED = "userBlocked";
    public static final String USER_INACTIVE = "userInactive";
    public static final String USER_NOT_VERIFIED = "userNotVerified";
    public static final String VERIFICATION_TOKEN_EXPIRED = "verificationTokenExpired";
    public static final String VERIFICATION_TOKEN_USED = "verificationTokenUsed";
    public static final String INTERNAL_SERVER_ERROR = "internalServerError";
    public static final String ADMINISTRATOR_OWN_ROLE_REMOVAL = "administratorOwnRoleRemoval";
    public static final String ADMINISTRATOR_OWN_BLOCK = "administratorOwnBlock";
    public static final String NOT_FOUND = "notFound";
    public static final String USER_NOT_FOUND = "userNotFound";
    public static final String THEME_NOT_FOUND = "themeNotFound";
    public static final String SOMETHING_WENT_WRONG = "somethingWentWrong";
    public static final String ACCESS_DENIED = "accessDenied";
    public static final String JWT_TOKEN_INVALID = "jwtTokenInvalid";
    public static final String VALIDATION_ERROR = "validationError";
    public static final String ROLLBACK = "rollback";
    public static final String UNEXPECTED_ROLLBACK = "unexpectedRollback";
    public static final String TRANSACTION = "transaction";
    public static final String ACCESS_LEVEL_ASSIGNED = "accessLevelAssigned";
    public static final String ACCESS_LEVEL_TAKEN = "accessLevelTaken";
    public static final String ROLE_REQUEST_ALREADY_EXISTS = "roleRequestAlreadyExists";
    public static final String USER_ALREADY_HAS_ROLE = "userAlreadyHasRole";
    public static final String LOCAL_NOT_FOUND = "localNotFound";
    public static final String INVALID_LOCAL_STATE_ARCHIVE = "invalidLocalStateArchive";
    public static final String LOCAL_NOT_ACTIVE = "localNotActive";
    public static final String LOCAL_NOT_INACTIVE = "localNotInactive";
    public static final String LOCAL_NOT_UNAPPROVED = "localNotUnapproved";
    public static final String WRONG_END_DATE = "wrongEndDate";
    public static final String RENT_NOT_FOUND = "rentNotFound";
    public static final String DATE_PARSING_ERROR = "dateParsingError";
    public static final String APPLICATION_EXISTS = "applicationExists";
    public static final String ADDRESS_ALREADY_ASSIGNED = "addressAlreadyAssigned";
    public static final String VARIABLE_FEE_ALREADY_EXISTS = "variableFeeAlreadyExists";
    public static final String RENT_ENDED = "rentEnded";
    public static final String PAYMENT_ALREADY_EXISTS = "paymentAlreadyExists";
    public static final String LOCAL_CREATION_ERROR = "localCreationFailed";
    public static final String IMAGE_NOT_FOUND = "imageNotFound";
}
