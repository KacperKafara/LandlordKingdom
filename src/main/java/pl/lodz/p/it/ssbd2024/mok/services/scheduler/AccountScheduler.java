package pl.lodz.p.it.ssbd2024.mok.services.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Log
@Transactional
public class AccountScheduler {
    private final UserRepository userRepository;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void removeExpiredAccounts() {
        log.info("Removing expired accounts");
        LocalDateTime time = LocalDateTime.now().minusHours(24);
        userRepository.deleteUsersByCreatedAtBeforeAndVerifiedIsFalse(time);
    }
}