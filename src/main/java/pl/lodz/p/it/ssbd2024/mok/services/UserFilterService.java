package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.model.UserFilter;

public interface UserFilterService {
    void createOrUpdate(UserFilter userFilter);
    UserFilter getFilterForCurrentUser() throws NotFoundException;
}
