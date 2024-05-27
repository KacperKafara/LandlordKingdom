package pl.lodz.p.it.ssbd2024.mol.services;

import org.springframework.security.access.prepost.PreAuthorize;

public interface MolEmailService {


    void sendApplicationAcceptedEmail(String to, String name, String lang);
}
