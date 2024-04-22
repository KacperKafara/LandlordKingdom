package pl.lodz.p.it.ssbd2024.mok.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Administrator;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;

import java.util.Optional;
import java.util.UUID;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final UserRepository userRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository, UserRepository userRepository) {
        this.administratorRepository = administratorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Administrator removeAdministratorAccessLevel(UUID id) throws AccessLevelAlreadyRemovedException {
        Administrator administrator = administratorRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        if(!administrator.isActive()){
            throw new AccessLevelAlreadyRemovedException(UserExceptionMessages.ACCESS_LEVEL_REMOVED);
        }
        administrator.setActive(false);
        return administratorRepository.saveAndFlush(administrator);
    }

    @Override
    public Administrator addAdministratorAccessLevel(UUID id) {
        Optional<Administrator> administratorOptional = administratorRepository.findByUserId(id);

        Administrator administrator = administratorOptional.orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
            Administrator newAdministrator = new Administrator();
            newAdministrator.setUser(user);
            return newAdministrator;
        });

        if (administrator.isActive()) {
            return administrator;
        }

        administrator.setActive(true);
        return administratorRepository.saveAndFlush(administrator);
    }
}
