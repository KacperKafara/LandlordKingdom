package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.mol.services.MolEmailService;
import pl.lodz.p.it.ssbd2024.services.HtmlEmailService;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MolEmailServiceImpl implements MolEmailService {

    HtmlEmailService htmlEmailService;

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void sendRoleRequestAcceptedEmail(String to, String name, String property, String lang){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public void sendRoleRequestRejectedEmail(String to, String name, String property, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendApplicationRejectedEmail(String to, String name, String property, String lang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
