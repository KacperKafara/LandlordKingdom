package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.security.access.prepost.PreAuthorize;

public interface MolEmailService {

    void sendApplicationForRoleAcceptedEmail(String to, String name, String property,  String lang);

    void sendApplicationForRoleRemovedEmail(String to, String name, String property,  String lang);
}
