package pl.lodz.p.it.ssbd2024.mok.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Administrator;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.util.Optional;
import java.util.UUID;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final EmailService emailService;
    private final AdministratorRepository administratorRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdministratorServiceImpl(EmailService emailService, AdministratorRepository administratorRepository, UserRepository userRepository) {
        this.emailService = emailService;
        this.administratorRepository = administratorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Administrator removeAdministratorAccessLevel(UUID id) throws NotFoundException {
        Administrator administrator = administratorRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        administrator.setActive(false);
        User user = administrator.getUser();

        emailService.sendAdministratorPermissionLostEmail(user.getEmail(), user.getFirstName(), "en");

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

        emailService.sendAdministratorPermissionGainedEmail(user.getEmail(), user.getFirstName(), "en");

        return administratorRepository.saveAndFlush(administrator);
    }
}
