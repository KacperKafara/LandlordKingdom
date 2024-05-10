package pl.lodz.p.it.ssbd2024.services;

import java.time.LocalDateTime;
import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel, String lang);

    void sendAccountActivationEmail(String to, String name, String uri, String lang);

    void sendLoginBlockEmail(String to, int loginNumber, LocalDateTime failedLoginTime, LocalDateTime unblockTime, String ip, String lang);

    void sendAccountBlockEmail(String to, String name, String lang);

    void sendAccountUnblockEmail(String to, String name, String lang);

    void sendEmailChangeEmail(String to, String name, String uri, String lang);

    void sendPasswordChangeEmail(String to, String name, String uri, String lang);

    void sendTenantPermissionGainedEmail(String to, String name, String lang);

    void sendTenantPermissionLostEmail(String to, String name, String lang);

    void sendOwnerPermissionGainedEmail(String to, String name, String lang);

    void sendOwnerPermissionLostEmail(String to, String name, String lang);

    void sendAdministratorPermissionGainedEmail(String to, String name, String lang);

    void sendAdministratorPermissionLostEmail(String to, String name, String lang);

    void sendAccountDeletedEmail(String to, String name, String lang);

    void sendAdminLoginEmail(String to, String name, String ip, String lang);
}
