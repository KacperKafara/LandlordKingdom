package pl.lodz.p.it.ssbd2024.mok.mappers;

import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.DetailedUserResponse;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;

import java.time.format.DateTimeFormatter;

public class UserMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLogin(),
                user.getLanguage());
    }

    public static DetailedUserResponse toDetailedUserResponse(User user) {
        String lastSuccessfulLogin = user.getLastSuccessfulLogin() != null ?
                user.getLastSuccessfulLogin().format(formatter) : null;
        String lastFailedLogin = user.getLastFailedLogin() != null ?
                user.getLastFailedLogin().format(formatter) : null;

        return new DetailedUserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLogin(),
                user.getLanguage(),
                lastSuccessfulLogin,
                lastFailedLogin,
                user.isBlocked(),
                user.isVerified());
    }
}
