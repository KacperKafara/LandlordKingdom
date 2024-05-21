package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
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
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class})
    public Page<Administrator> getAllFiltered(Specification<Administrator> specification, Pageable pageable) {
        return administratorRepository.findAll(specification, pageable);
    }


    @Override
    public Administrator removeAdministratorAccessLevel(UUID id) throws NotFoundException {
        Administrator administrator = administratorRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        administrator.setActive(false);
        User user = administrator.getUser();

        emailService.sendAdministratorPermissionLostEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return administratorRepository.saveAndFlush(administrator);
    }

    @Override
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
