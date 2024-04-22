package pl.lodz.p.it.ssbd2024.mok.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.messages.UserExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.TenantService;

import java.util.Optional;
import java.util.UUID;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;

    public TenantServiceImpl(TenantRepository tenantRepository, UserRepository userRepository) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Tenant addTenantAccessLevel(UUID id) {
        Optional<Tenant> tenantOptional = tenantRepository.findByUserId(id);

        Tenant tenant = tenantOptional.orElseGet(() -> {
            User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionMessages.NOT_FOUND));
            Tenant newTenant = new Tenant();
            newTenant.setUser(user);
            return newTenant;
        });

        if (tenant.isActive()) {
            return tenant;
        }

        tenant.setActive(true);
        return tenantRepository.saveAndFlush(tenant);
    }
}
