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
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.TenantService;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;

import java.util.Optional;
import java.util.UUID;

@Transactional(rollbackFor = NotFoundException.class)
@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final EmailService emailService;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Transactional(rollbackFor = {SemanticException.class, PathElementException.class})
    public Page<Tenant> getAllFiltered(Specification<Tenant> specification, Pageable pageable) {
        return tenantRepository.findAll(specification, pageable);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Tenant removeTenantAccessLevel(UUID id) throws NotFoundException {
        Tenant tenant = tenantRepository.findByUserId(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));

        tenant.setActive(false);
        User user = tenant.getUser();

        emailService.sendTenantPermissionLostEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return tenantRepository.saveAndFlush(tenant);
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public Tenant addTenantAccessLevel(UUID id) throws NotFoundException {
        Optional<Tenant> tenantOptional = tenantRepository.findByUserId(id);

        Tenant tenant;
        if (tenantOptional.isPresent()) {
            tenant = tenantOptional.get();
        } else {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
            tenant = new Tenant();
            tenant.setUser(user);
        }

        if (tenant.isActive()) {
            return tenant;
        }

        tenant.setActive(true);
        User user = tenant.getUser();

        emailService.sendTenantPermissionGainedEmail(user.getEmail(), user.getFirstName(), user.getLanguage());

        return tenantRepository.saveAndFlush(tenant);
    }
}
