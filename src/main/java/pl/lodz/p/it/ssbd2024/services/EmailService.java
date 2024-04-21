package pl.lodz.p.it.ssbd2024.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
