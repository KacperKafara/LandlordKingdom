package pl.lodz.p.it.ssbd2024.mok.services.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.mok.dto.AuthenticationResponse;
import pl.lodz.p.it.ssbd2024.mok.repositories.AdministratorRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.OwnerRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.TenantRepository;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;
import pl.lodz.p.it.ssbd2024.mok.services.AuthenticationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository repository;
    private final OwnerRepository ownerRepository;
    private final TenantRepository tenantRepository;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(JwtService jwtService, UserRepository repository, OwnerRepository ownerRepository, TenantRepository tenantRepository, AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.ownerRepository = ownerRepository;
        this.tenantRepository = tenantRepository;
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<String> getUserRoles(UUID userId, boolean active) {
        List<String> roles = new ArrayList<>();

        ownerRepository.findByUserIdAndActive(userId, active).ifPresent(owner -> roles.add("owner"));
        tenantRepository.findByUserIdAndActive(userId, active).ifPresent(tenant -> roles.add("tenant"));
        administratorRepository.findByUserIdAndActive(userId, active).ifPresent(admin -> roles.add("administrator"));

        return roles;
    }

    @Override
    public AuthenticationResponse authenticate(String login, String password) {
        var user = repository.findByLogin(login).orElseThrow();
        if (!passwordEncoder.matches(password, user.getPassword())) { throw  new RuntimeException("Wrong password");}
        var jwtToken = (jwtService.generateToken(user.getId(), getUserRoles(user.getId(), !user.isBlocked())));
        return AuthenticationResponse.builder()
                .token(String.valueOf(jwtToken))
                .build();
    }
}
