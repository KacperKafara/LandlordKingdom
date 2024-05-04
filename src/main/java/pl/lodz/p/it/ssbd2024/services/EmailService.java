package pl.lodz.p.it.ssbd2024.services;

import java.util.Map;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String body);

    void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel);
}
