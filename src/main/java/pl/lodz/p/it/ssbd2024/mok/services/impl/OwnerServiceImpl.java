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
import pl.lodz.p.it.ssbd2024.model.Owner;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.OwnerService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;

import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackFor = NotFoundException.class)
@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {
    private final EmailService emailService;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class})
    public Page<Owner> getAllFiltered(Specification<Owner> specification, Pageable pageable) {
        return ownerRepository.findAll(specification, pageable);
    }

    @Override
    public Owner removeOwnerAccessLevel(UUID id) throws NotFoundException {
        Owner owner = ownerRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        owner.setActive(false);
        User user = owner.getUser();

        emailService.sendOwnerPermissionLostEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return ownerRepository.saveAndFlush(owner);
    }

    @Override
    public Owner addOwnerAccessLevel(UUID id) throws NotFoundException {
        Optional<Owner> ownerOptional = ownerRepository.findByUserId(id);

        Owner owner;
        if (ownerOptional.isPresent()) {
            owner = ownerOptional.get();
        } else {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
            owner = new Owner();
            owner.setUser(user);
        }

        if (owner.isActive()) {
            return owner;
        }

        owner.setActive(true);
        User user = owner.getUser();

        emailService.sendOwnerPermissionGainedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return ownerRepository.saveAndFlush(owner);
    }
}
