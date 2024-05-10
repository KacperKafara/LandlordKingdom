package pl.lodz.p.it.ssbd2024.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import pl.lodz.p.it.ssbd2024.services.EmailService;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

@Log
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private final ResourceBundleMessageSource mailMessageSource;

    @Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.warning("Failed to send email.");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel, String lang) {
        Context thymeleafContext = new Context(Locale.of(lang));
        thymeleafContext.setVariables(templateModel);
        String htmlBody = templateEngine.process(templateName, thymeleafContext);
        sendHtmlEmail(to, subject, htmlBody);
    }

    @Override
    public void sendAccountActivationEmail(String to, String name, String uri, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "url", uri);
        String subject = mailMessageSource.getMessage("accountConfirm.subject", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "accountConfirm", templateModel, lang);
    }


    @Override
    public void sendLoginBlockEmail(String to, int loginNumber, LocalDateTime failedLoginTime, LocalDateTime unblockTime, String ip, String lang) {
        Map<String, Object> templateModel = Map.of(
                "loginNumber", loginNumber,
                "failedLoginTime", failedLoginTime,
                "unblockTime", unblockTime,
                "ip", ip);
        String subject = mailMessageSource.getMessage("loginBlock.subject", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "loginBlock", templateModel, lang);
    }

    @Override
    public void sendAccountBlockEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("accountBlockChange.block", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status);
        String subject = mailMessageSource.getMessage("accountBlockChange.subjectBlock", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "accountBlockChange", templateModel, lang);
    }

    @Override
    public void sendAccountUnblockEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("accountBlockChange.unblock", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status);
        String subject = mailMessageSource.getMessage("accountBlockChange.subjectUnblock", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "accountBlockChange", templateModel, lang);
    }

    @Override
    public void sendEmailChangeEmail(String to, String name, String uri, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "url", uri);
        String subject = mailMessageSource.getMessage("emailChange.subject", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "emailChange", templateModel, lang);
    }

    @Override
    public void sendPasswordChangeEmail(String to, String name, String uri, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "url", uri);
        String subject = mailMessageSource.getMessage("passwordChange.subject", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "passwordChange", templateModel, lang);
    }

    @Override
    public void sendTenantPermissionGainedEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("permissionChange.gain", null, Locale.of(lang));
        String permissions = mailMessageSource.getMessage("permissionChange.tenant", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status,
                "permissions", permissions);
        String subject = mailMessageSource.getMessage("permissionChange.subjectGain", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendTenantPermissionLostEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("permissionChange.lost", null, Locale.of(lang));
        String permissions = mailMessageSource.getMessage("permissionChange.tenant", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status,
                "permissions", permissions);
        String subject = mailMessageSource.getMessage("permissionChange.subjectLost", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendOwnerPermissionGainedEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("permissionChange.gain", null, Locale.of(lang));
        String permissions = mailMessageSource.getMessage("permissionChange.owner", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status,
                "permissions", permissions);
        String subject = mailMessageSource.getMessage("permissionChange.subjectGain", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendOwnerPermissionLostEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("permissionChange.lost", null, Locale.of(lang));
        String permissions = mailMessageSource.getMessage("permissionChange.owner", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status,
                "permissions", permissions);
        String subject = mailMessageSource.getMessage("permissionChange.subjectLost", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendAdministratorPermissionGainedEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("permissionChange.gain", null, Locale.of(lang));
        String permissions = mailMessageSource.getMessage("permissionChange.administrator", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status,
                "permissions", permissions);
        String subject = mailMessageSource.getMessage("permissionChange.subjectGain", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendAdministratorPermissionLostEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("permissionChange.lost", null, Locale.of(lang));
        String permissions = mailMessageSource.getMessage("permissionChange.administrator", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status,
                "permissions", permissions);
        String subject = mailMessageSource.getMessage("permissionChange.subjectLost", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendAccountDeletedEmail(String to, String name, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name);
        String subject = mailMessageSource.getMessage("accountDelete.subject", null, Locale.of(lang));

        sendHtmlEmail(to, subject, "accountDelete", templateModel, lang);
    }
}
