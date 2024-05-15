package pl.lodz.p.it.ssbd2024.aspects;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionSynchronization;

@Slf4j
public class TransactionSynchronizationImpl implements TransactionSynchronization {
    private String txKey;

    public TransactionSynchronizationImpl(String txKey) {
        this.txKey = txKey;
    }

    @Override
    public void afterCompletion(int status) {
        String statusString = switch (status) {
            case STATUS_COMMITTED -> "COMMITTED";
            case STATUS_ROLLED_BACK -> "ROLLED_BACK";
            case STATUS_UNKNOWN -> "UNKNOWN";
            default -> throw new IllegalArgumentException("Unexpected transaction status: " + status);
        };
        log.info("Transaction: " + txKey + " completed with status: " + statusString);
    }
}
