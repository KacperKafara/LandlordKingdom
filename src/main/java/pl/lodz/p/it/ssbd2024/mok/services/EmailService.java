package pl.lodz.p.it.ssbd2024.mok.services;

public interface EmailService {
    void sendAccountActivationEmail(String to, String name, String uri, String lang);

    void sendLoginBlockEmail(String to, int loginNumber, String failedLoginTime, String unblockTime, String ip, String lang);

    void sendAccountBlockEmail(String to, String name, String lang);

    void sendAccountUnblockEmail(String to, String name, String lang);

    void sendAccountActivatedEmail(String to, String name, String lang);

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

    void sendOTPEmail(String to, String name, String otp, String lang);
}
