package pl.lodz.p.it.ssbd2024.mok.mappers;

import org.springframework.data.domain.Page;
import pl.lodz.p.it.ssbd2024.model.AccessLevel;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.FilteredUsersResponse;

public class FilteredUsersMapper {

    public static FilteredUsersResponse accesslevelToFilteredUsersResponse(Page<? extends AccessLevel> result) {
        return new FilteredUsersResponse(result.stream().map(AccessLevel::getUser).map(UserMapper::toUserResponse).toList(),
                result.getTotalPages());
    }

    public static FilteredUsersResponse userToFilteredUsersResponse(Page<User> result) {
        return new FilteredUsersResponse(result.stream().map(UserMapper::toUserResponse).toList(),
                result.getTotalPages());
    }


}
