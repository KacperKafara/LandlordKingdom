package pl.lodz.p.it.ssbd2024.mok.services;

import pl.lodz.p.it.ssbd2024.model.Tenant;

import java.util.UUID;

public interface TenantService {
    Tenant addTenantAccessLevel(UUID id);
}
