package pl.lodz.p.it.ssbd2024.services;

import java.util.Map;

public interface HtmlEmailService {
    void sendHtmlEmail(String to, String subject, String body);

    void createHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel, String lang);
}
