package pl.lodz.p.it.ssbd2024.integration;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.util.*;

public class EmailReader extends BaseConfig {

    private static final String OTP_SUBJECT = "OTP code";

    private static JavaMailSenderImpl configureMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(smtp.getHost());
        mailSender.setPort(smtp.getMappedPort(143));
        mailSender.setUsername("user");
        mailSender.setPassword("user");

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.store.protocol", "imap");

        return mailSender;
    }

    public static String readOtpFromEmail(String userMailAddress) throws MessagingException, IOException, InterruptedException {
        Thread.sleep(2000);
        JavaMailSenderImpl mailSender = configureMailSender();

        try (Store store = mailSender.getSession().getStore("imap")) {
            store.connect(smtp.getHost(), smtp.getMappedPort(143), "user", "user");

            try (Folder inbox = store.getFolder("INBOX")) {
                inbox.open(Folder.READ_ONLY);

                Message[] messages = inbox.getMessages();
                List<Message> filteredMessages = filterMessages(messages, userMailAddress);

                if (!filteredMessages.isEmpty()) {
                    filteredMessages.sort(Comparator.comparing((Message message) -> {
                        try {
                            return message.getReceivedDate();
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                    }).reversed());
                    Message mostRecentMessage = filteredMessages.getFirst();
                    return extractOTPFromMessage(mostRecentMessage);
                } else {
                    throw new RuntimeException("Email with subject 'OTP code' not found for user '" + userMailAddress + "'.");
                }
            }
        }
    }

    private static List<Message> filterMessages(Message[] messages, String userMailAddress) throws MessagingException {
        List<Message> filteredMessages = new ArrayList<>();
        for (Message message : messages) {
            Address[] recipientAddresses = message.getAllRecipients();
            if (recipientAddresses != null) {
                for (Address recipient : recipientAddresses) {
                    if (recipient.toString().contains(userMailAddress) && OTP_SUBJECT.equals(message.getSubject())) {
                        filteredMessages.add(message);
                        break;
                    }
                }
            }
        }
        return filteredMessages;
    }


    public static String extractOTPFromMessage(Message message) throws MessagingException, IOException {
        StringBuilder emailContentBuilder = new StringBuilder();

        Enumeration<Header> headers = message.getAllHeaders();
        while (headers.hasMoreElements()) {
            Header header = headers.nextElement();
            emailContentBuilder.append(header.getName()).append(": ").append(header.getValue()).append("\n");
        }

        Object content = message.getContent();
        if (content instanceof MimeMultipart mimeMultipart) {
            int count = mimeMultipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart bodyPart = mimeMultipart.getBodyPart(i);
                extractBodyPartContent(bodyPart, emailContentBuilder);
            }
        } else if (content instanceof String) {
            emailContentBuilder.append("Content: ").append(content).append("\n");
        }

        String emailContent = emailContentBuilder.toString();

        String otpPattern = "\\b\\d{8}\\b";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(otpPattern);
        java.util.regex.Matcher matcher = pattern.matcher(emailContent);
        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private static void extractBodyPartContent(BodyPart bodyPart, StringBuilder emailContentBuilder) throws MessagingException, IOException {
        Object bodyPartContent = bodyPart.getContent();
        if (bodyPartContent instanceof MimeMultipart multipart) {
            int count = multipart.getCount();
            for (int i = 0; i < count; i++) {
                BodyPart part = multipart.getBodyPart(i);
                extractBodyPartContent(part, emailContentBuilder);
            }
        } else if (bodyPartContent instanceof String) {
            emailContentBuilder.append("Content-Type: ").append(bodyPart.getContentType()).append("\n");
            emailContentBuilder.append("Content: ").append(bodyPartContent).append("\n");
        }
    }

}
