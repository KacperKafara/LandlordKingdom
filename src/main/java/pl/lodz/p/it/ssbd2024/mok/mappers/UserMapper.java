package pl.lodz.p.it.ssbd2024.mok.mappers;

import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail(), user.getLogin(), user.getLanguage());
    }
}
