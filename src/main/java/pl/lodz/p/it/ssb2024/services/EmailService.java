package pl.lodz.p.it.ssb2024.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
