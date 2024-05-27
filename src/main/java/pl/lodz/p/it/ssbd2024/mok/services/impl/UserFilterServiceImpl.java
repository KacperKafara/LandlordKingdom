package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.model.UserFilter;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserFilterRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.UserFilterService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserFilterServiceImpl implements UserFilterService {
    private final UserFilterRepository userFilterRepository;
    private final UserRepository userRepository;

    @Override
    public void createOrUpdate(UserFilter userFilter) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        UserFilter optionalUserFilter =
                userFilterRepository.findByUserId(userId).orElseGet(() -> {
                    UserFilter newUserFilter = new UserFilter();
                    newUserFilter.setUser(userRepository.getReferenceById(userId));
                    return newUserFilter;
                });

        optionalUserFilter.setLogin(userFilter.getLogin());
        optionalUserFilter.setEmail(userFilter.getEmail());
        optionalUserFilter.setFirstName(userFilter.getFirstName());
        optionalUserFilter.setLastName(userFilter.getLastName());
        optionalUserFilter.setRole(userFilter.getRole());
        optionalUserFilter.setBlocked(userFilter.getBlocked());
        optionalUserFilter.setVerified(userFilter.getVerified());
        optionalUserFilter.setPageSize(userFilter.getPageSize());

        userFilterRepository.saveAndFlush(optionalUserFilter);
    }

    @Override
    public UserFilter getFilterForCurrentUser() throws NotFoundException {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        return userFilterRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("filter not found", ErrorCodes.NOT_FOUND));
    }
}
