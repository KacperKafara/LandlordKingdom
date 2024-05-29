package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.security.access.prepost.PreAuthorize;

public interface MolEmailService {

    void sendRoleRequestAcceptedEmail(String to, String name, String property,  String lang);

    void sendRoleRequestRejectedEmail(String to, String name, String property,  String lang);

    void sendApplicationRejectedEmail(String to, String name, String property, String lang);
}
