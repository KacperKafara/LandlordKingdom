package pl.lodz.p.it.ssbd2024.messages;

public class ExceptionMessages {
    public static final String MEDIA_NOT_SUPPORTED = "Unsupported media type - JSON only";
    public static final String VALIDATION_ERROR = "Validation error that occurred during processing the data: ";
    public static final String TEMPLATE_ERROR = "Problem occurred while trying to send email";
    public static final String JDBC_ERROR = "Problem occurred while trying to connect to the database";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String UNCAUGHT = "Internal server error";
    public static final String ACCESS_DENIED = "Access denied";
    public static final String OPTIMISTIC_LOCK = "There was a conflict with the data you are trying to access. Please try again.";
    public static final String PERSISTENCE_ERROR = "Problem occurred while trying to persist data";
    public static final String NOT_FOUND = "Endpoint not found";
    public static final String ROLLBACK = "Problem occurred during transaction, try again later";
    public static final String UNEXPECTED_ROLLBACK = "Problem occurred during transaction, try again later";
    public static final String TRANSACTION = "Problem occurred during transaction, try again later";
}
