package pl.lodz.p.it.ssbd2024.mok.services.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Log
public class AccountScheduler {
    private final UserService userService;

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
    public void removeExpiredAccounts() {
        log.info("Removing expired accounts");
        userService.deleteNonVerifiedUsers();
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.MINUTES)
    public void resendVerificationEmails() {
        log.info("Resending verification emails");
        userService.sendEmailVerifyAccount();
    }
}