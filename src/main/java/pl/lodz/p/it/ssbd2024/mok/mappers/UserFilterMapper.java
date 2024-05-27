package pl.lodz.p.it.ssbd2024.mok.mappers;

import pl.lodz.p.it.ssbd2024.model.UserFilter;
import pl.lodz.p.it.ssbd2024.mok.dto.FilteredUsersRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserFilterResponse;

public class UserFilterMapper {
    private UserFilterMapper() {
    }

    public static UserFilter toUserFilter(FilteredUsersRequest request) {
        UserFilter filter = new UserFilter();
        filter.setRole(request.role());
        request.searchCriteriaList().forEach(criteria -> {
            String value = criteria.getValue().toString();
            switch (criteria.getFilterKey()) {
                case "firstName":
                    filter.setFirstName(value);
                    break;
                case "lastName":
                    filter.setLastName(value);
                    break;
                case "email":
                    filter.setEmail(value);
                    break;
                case "login":
                    filter.setLogin(value);
                    break;
                case "blocked":
                    filter.setBlocked(value != null ? Boolean.parseBoolean(value) : null);
                    break;
                case "verified":
                    filter.setVerified(value != null ? Boolean.parseBoolean(value) : null);
                    break;
                default:
                    break;
            }
        });

        return filter;
    }

    public static UserFilterResponse toUserFilterResponse(UserFilter userFilter) {
        return new UserFilterResponse(
                userFilter.getLogin(),
                userFilter.getEmail(),
                userFilter.getFirstName(),
                userFilter.getLastName(),
                userFilter.isBlocked(),
                userFilter.isVerified(),
                userFilter.getRole()
        );

    }
}
