package pl.lodz.p.it.ssbd2024.services.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import pl.lodz.p.it.ssbd2024.services.HtmlEmailService;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
@Transactional(propagation = Propagation.MANDATORY)
@RequiredArgsConstructor
public class HtmlEmailServiceImpl implements HtmlEmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    @PreAuthorize("permitAll()")
    public void sendHtmlEmail(String to, String subject, String body) {
        log.info("eluwina");
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.warn("Failed to send email.");
            throw new RuntimeException(e);
        }

    }

    @Override
    @PreAuthorize("permitAll()")
    public void createHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel, String lang) {
        Context thymeleafContext = new Context(Locale.of(lang));
        thymeleafContext.setVariables(templateModel);
        String htmlBody = templateEngine.process(templateName, thymeleafContext);
        sendHtmlEmail(to, subject, htmlBody);
    }
}
