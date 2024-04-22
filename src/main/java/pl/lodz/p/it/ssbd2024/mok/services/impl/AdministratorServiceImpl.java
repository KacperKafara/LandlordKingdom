package pl.lodz.p.it.ssbd2024.mok.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.exceptions.AccessLevelAlreadyRemovedException;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Administrator;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;

import java.util.UUID;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository administratorRepository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public Administrator removeAdministratorAccessLevel(UUID id) throws AccessLevelAlreadyRemovedException, NotFoundException {
        Administrator administrator = administratorRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
        if(!administrator.isActive()){
            throw new AccessLevelAlreadyRemovedException(UserExceptionMessages.ACCESS_LEVEL_REMOVED);
        }
        administrator.setActive(false);
        return administratorRepository.saveAndFlush(administrator);
    }
}
