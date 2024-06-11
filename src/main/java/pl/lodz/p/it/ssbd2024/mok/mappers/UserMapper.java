package pl.lodz.p.it.ssbd2024.mok.mappers;

import lombok.extern.slf4j.Slf4j;
import pl.lodz.p.it.ssbd2024.model.Timezone;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.DetailedUserResponse;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.util.DateUtils;

import java.util.List;

@Slf4j
public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        String timezone = user.getTimezone() != null ?
                user.getTimezone().getName() : "UTC";

        return new UserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLogin(),
                user.getLanguage(),
                user.isBlocked(),
                timezone);
    }

    public static DetailedUserResponse toDetailedUserResponse(User user, List<String> roles) {
        String timezone = user.getTimezone() != null ?
                user.getTimezone().getName() : "UTC";

        String theme = user.getTheme() != null ?
                user.getTheme().getType() : "LIGHT";

        String lastSuccessfulLogin = null;
        String lastFailedLogin = null;

        if (user.getLastSuccessfulLogin() != null) {
            lastSuccessfulLogin = DateUtils.convertUTCToAnotherTimezoneSimple(user.getLastSuccessfulLogin(), timezone, user.getLanguage());
        }

        if (user.getLastFailedLogin() != null) {
            lastFailedLogin = DateUtils.convertUTCToAnotherTimezoneSimple(user.getLastFailedLogin(), timezone, user.getLanguage());
        }

        return new DetailedUserResponse(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getLogin(),
                user.getLanguage(),
                lastSuccessfulLogin,
                lastFailedLogin,
                user.getLastSuccessfulLoginIp(),
                user.getLastFailedLoginIp(),
                timezone,
                user.isBlocked(),
                user.isVerified(),
                user.isActive(),
                roles,
                theme);
    }

    public static User toUser(UpdateUserDataRequest userRequest, Timezone timezone) {
        User user = new User();
        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setLanguage(userRequest.language());
        user.setTimezone(timezone);
        return user;
    }
}
