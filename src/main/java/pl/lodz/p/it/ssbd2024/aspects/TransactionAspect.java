package pl.lodz.p.it.ssbd2024.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.lodz.p.it.ssbd2024.model.AbstractEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class TransactionAspect {
    @Pointcut("@within(transactional)")
    public void transactionalMethods(Transactional transactional) {
    }

    @Pointcut("execution(* org.springframework.data.jpa.repository.JpaRepository+.*(..))")
    public void repositoryMethods() {
    }

    @Value("${transaction.timeout}")
    private int transactionTimeout;

    @Around(value = "transactionalMethods(transactional)", argNames = "jp, transactional")
    public Object logTransaction(ProceedingJoinPoint jp, Transactional transactional) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = "<anonymous>";

        if(authentication != null) {
            try {
                Jwt jwt = (Jwt) authentication.getPrincipal();
                username = jwt.getClaim("login").toString();
            } catch (Exception e) {
                username = "<anonymous>";
            }
        }
        String callerClass = jp.getTarget().getClass().getName();
        String callerMethod = jp.getSignature().getName();
        String txId = UUID.randomUUID().toString();

        if(TransactionSynchronizationManager.isActualTransactionActive()) {
            String id;
            try {
                UUID uuidId = UUID.fromString(Objects.requireNonNull(TransactionSynchronizationManager.getCurrentTransactionName()));
                id = uuidId.toString();
                log.info("Continuing existing transaction {} with propagation {} in method {}.{}", id, transactional.propagation(), callerClass, callerMethod);
            } catch (IllegalArgumentException ignored) {
                id = txId;
                TransactionSynchronizationManager.setCurrentTransactionName(id);
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationImpl(id));
                log.info("Transaction {} started by {} in {}.{}", id, username, callerClass, callerMethod);
                int timeout = transactional.timeout() > 0 ? transactional.timeout() : transactionTimeout;
                log.info("Transaction {} info: propagation={}, isolation={}, readOnly={}, timeout={}", id, transactional.propagation(), transactional.isolation(), transactional.readOnly(), timeout);
            }
            String args = parsArgs(jp.getArgs());
            if (args != null) {
                log.info("Method {}.{} called by {} called in transaction {} with args: {}", callerClass, callerMethod, username, id, args);
            } else {
                log.info("Method {}.{} called by {} called in transaction {}", callerClass, callerMethod, username, id);
            }
            Object obj;
            try {
                obj = jp.proceed();
            } catch (Throwable e) {
                log.error("Method {}.{} called by {} failed in transaction {} due {} with message {}", callerClass, callerMethod, username, id, e.getClass().getName(), e.getMessage(), e);
                throw e;
            }
            String returnValue = parseReturnValue(obj);
            if (returnValue != null) {
                log.info("Method {}.{} called by {} returned in transaction {} with: {}", callerClass, callerMethod, username, id, returnValue);
            } else {
                log.info("Method {}.{} called by {} returned in transaction {}", callerClass, callerMethod, username, id);
            }
            return obj;
        } else {
            TransactionSynchronizationManager.setCurrentTransactionName(txId);
            String args = parsArgs(jp.getArgs());
            if (args != null) {
                log.info("Method {}.{} called by {} with args: {}", callerClass, callerMethod, username, args);
            } else {
                log.info("Method {}.{} called by {}", callerClass, callerMethod, username);
            }
            Object obj;
            try {
                obj = jp.proceed();
            } catch (Throwable e) {
                log.error("Method {}.{} called by {} failed due {} with message {}", callerClass, callerMethod, username, e.getClass().getName(), e.getMessage(), e);
                throw e;
            }
            String returnValue = parseReturnValue(obj);
            if (returnValue != null) {
                log.info("Method {}.{} called by {} returned with: {}", callerClass, callerMethod, username, returnValue);
            } else {
                log.info("Method {}.{} called by {} returned", callerClass, callerMethod, username);
            }
            return obj;
        }
    }

    String parsArgs(Object[] args) {
        if (args == null || args.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            if(arg == null) {
                continue;
            }
            if (arg instanceof AbstractEntity abstractEntity) {
                sb
                        .append(abstractEntity.getClass().getName())
                        .append(abstractEntity)
                        .append(", ");
            } else {
                sb.append(arg).append(", ");
            }

            if (arg instanceof List<?> entities) {
                for (Object entity : entities) {
                    if(entity == null) {
                        continue;
                    }
                    if (entity instanceof AbstractEntity abstractEntity) {
                        sb
                                .append(abstractEntity.getClass().getName())
                                .append(abstractEntity)
                                .append(", ");
                    } else {
                        sb.append(entity).append(", ");
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
                    } else {
                        sb.append(entity.toString()).append(", ");
                    }
                }
                yield sb.toString();
            }
            case Object object -> object.toString();
        };
    }
}
