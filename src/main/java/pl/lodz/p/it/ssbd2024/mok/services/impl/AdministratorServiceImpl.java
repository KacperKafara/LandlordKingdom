package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.AdministratorOwnRoleRemovalException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.AdministratorMessages;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Administrator;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;

import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackFor = NotFoundException.class)
@Service
@RequiredArgsConstructor
public class AdministratorServiceImpl implements AdministratorService {
    private final EmailService emailService;
    private final AdministratorRepository administratorRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class})
    public Page<Administrator> getAllFiltered(Specification<Administrator> specification, Pageable pageable) {
        return administratorRepository.findAll(specification, pageable);
    }


    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Administrator removeAdministratorAccessLevel(UUID id, UUID administratorId) throws NotFoundException, AdministratorOwnRoleRemovalException {
        Administrator administrator = administratorRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        if(administrator.getUser().getId().equals(administratorId)){
            throw new AdministratorOwnRoleRemovalException(AdministratorMessages.OWN_ADMINISTRATOR_ROLE_REMOVAL, ErrorCodes.ADMINISTRATOR_OWN_ROLE_REMOVAL);
        }

        administrator.setActive(false);
        User user = administrator.getUser();

        emailService.sendAdministratorPermissionLostEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return administratorRepository.saveAndFlush(administrator);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Administrator addAdministratorAccessLevel(UUID id) throws NotFoundException {
        Optional<Administrator> administratorOptional = administratorRepository.findByUserId(id);

        Administrator administrator;
        if (administratorOptional.isPresent()) {
            administrator = administratorOptional.get();
        } else {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
            administrator = new Administrator();
            administrator.setUser(user);
        }

        if (administrator.isActive()) {
            return administrator;
        }

        administrator.setActive(true);
        User user = administrator.getUser();

        emailService.sendAdministratorPermissionGainedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return administratorRepository.saveAndFlush(administrator);
    }
}
