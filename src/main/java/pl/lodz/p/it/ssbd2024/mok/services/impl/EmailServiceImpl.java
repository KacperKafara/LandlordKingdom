package pl.lodz.p.it.ssbd2024.mok.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.mok.services.EmailService;
import pl.lodz.p.it.ssbd2024.services.HtmlEmailService;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final ResourceBundleMessageSource mailMessageSource;
    private final HtmlEmailService htmlEmailService;

    @Value("${app.url}")
    private String webUrl;

    @Override
    public void sendAccountActivationEmail(String to, String name, String uri, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "url", uri);
        String subject = mailMessageSource.getMessage("accountConfirm.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "accountConfirm", templateModel, lang);
    }


    @Override
    public void sendLoginBlockEmail(String to, int loginNumber, String failedLoginTime, String unblockTime, String ip, String lang) {
        Map<String, Object> templateModel = Map.of(
                "loginNumber", loginNumber,
                "failedLoginTime", failedLoginTime,
                "unblockTime", unblockTime,
                "ip", ip);
        String subject = mailMessageSource.getMessage("loginBlock.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "loginBlock", templateModel, lang);
    }

    @Override
    public void sendAccountBlockEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("accountBlockChange.block", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status);
        String subject = mailMessageSource.getMessage("accountBlockChange.subjectBlock", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "accountBlockChange", templateModel, lang);
    }

    @Override
    public void sendAccountUnblockEmail(String to, String name, String lang) {
        String status = mailMessageSource.getMessage("accountBlockChange.unblock", null, Locale.of(lang));
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "status", status);
        String subject = mailMessageSource.getMessage("accountBlockChange.subjectUnblock", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "accountBlockChange", templateModel, lang);
    }

    @Override
    public void sendAccountActivatedEmail(String to, String name, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name, "url", webUrl);
        String subject = mailMessageSource.getMessage("accountActivated.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "accountActivated", templateModel, lang);
    }

    @Override
    public void sendEmailChangeEmail(String to, String name, String uri, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "url", uri);
        String subject = mailMessageSource.getMessage("emailChange.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "emailChange", templateModel, lang);
    }

    @Override
    public void sendPasswordChangeEmail(String to, String name, String uri, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "url", uri);
        String subject = mailMessageSource.getMessage("passwordChange.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "passwordChange", templateModel, lang);
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

        htmlEmailService.sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
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

        htmlEmailService.sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
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

        htmlEmailService.sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
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

        htmlEmailService.sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
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

        htmlEmailService.sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
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

        htmlEmailService.sendHtmlEmail(to, subject, "permissionChange", templateModel, lang);
    }

    @Override
    public void sendAccountDeletedEmail(String to, String name, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name);
        String subject = mailMessageSource.getMessage("accountDelete.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "accountDelete", templateModel, lang);
    }

    @Override
    public void sendAdminLoginEmail(String to, String name, String ip, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "ip", ip);
        String subject = mailMessageSource.getMessage("adminLogin.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "adminLogin", templateModel, lang);
    }

    @Override
    public void sendOTPEmail(String to, String name, String otp, String lang) {
        Map<String, Object> templateModel = Map.of(
                "name", name,
                "otp", otp);
        String subject = mailMessageSource.getMessage("OTP.subject", null, Locale.of(lang));

        htmlEmailService.sendHtmlEmail(to, subject, "OTP", templateModel, lang);
    }
}
