package pl.lodz.p.it.ssbd2024.aspects;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.lodz.p.it.ssbd2024.model.AbstractEntity;

import java.util.List;
import java.util.UUID;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TransactionAspect {
    @Pointcut("@within(transactional)")
    public void transactionalMethods(Transactional transactional) {
    }

    @Around(value = "transactionalMethods(transactional)", argNames = "jp, transactional")
    public Object logTransaction(ProceedingJoinPoint jp, Transactional transactional) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "<anonymous>";
        String callerClass = jp.getTarget().getClass().getName();
        String callerMethod = jp.getSignature().getName();
        String txId = UUID.randomUUID().toString();
        log.info("Transaction {} started by {} in {}.{}", txId, username, callerClass, callerMethod);
        log.info("Transaction {} info: propagation={}, isolation={}, readOnly={}, timeout={}", txId, transactional.propagation(), transactional.isolation(), transactional.readOnly(), transactional.timeout());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationImpl(txId));
        String args = parsArgs(jp.getArgs());
        if (args != null) {
            log.info("Method {} called in transaction {} with args: {}", callerMethod, txId, args);
        } else {
            log.info("Method {} called in transaction {}", callerMethod, txId);
        }
        Object obj;
        try {
            obj = jp.proceed();
        } catch (Throwable e) {
            log.error("Method {} failed in transaction {}", callerMethod, txId, e);
            throw e;
        }
        String returnValue = parseReturnValue(obj);
        if (returnValue != null) {
            log.info("Method {} returned in transaction {} with: {}", callerMethod, txId, returnValue);
        } else {
            log.info("Method {} returned in transaction {}", callerMethod, txId);
        }
        return obj;
    }

    String parsArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            if (arg instanceof AbstractEntity abstractEntity) {
                sb
                        .append(abstractEntity.getClass().getName())
                        .append(abstractEntity)
                        .append(", ");
            }

            if (arg instanceof List<?> entities) {
                for (Object entity : entities) {
                    if (entity instanceof AbstractEntity abstractEntity) {
                        sb
                                .append(abstractEntity.getClass().getName())
                                .append(abstractEntity)
                                .append(", ");
                    }
                }
            }
        }
        if (sb.isEmpty()) {
            return null;
        }
        return sb.toString();
    }

    String parseReturnValue(Object args) {
        if (args == null) {
            return null;
        }

        return switch (args) {
            case AbstractEntity entity -> entity.getClass().getName() + entity;
            case List<?> entities -> {
                StringBuilder sb = new StringBuilder();
                for (Object entity : entities) {
                    if (entity instanceof AbstractEntity abstractEntity) {
                        sb.append(abstractEntity).append(", ");
                    }
                }
                yield sb.toString();
            }
            default -> null;
        };
    }
}
