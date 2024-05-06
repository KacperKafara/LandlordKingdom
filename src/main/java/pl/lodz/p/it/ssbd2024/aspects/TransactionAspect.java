package pl.lodz.p.it.ssbd2024.aspects;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.lodz.p.it.ssbd2024.model.AbstractEntity;

import java.util.List;
import java.util.UUID;

@Log
@Aspect
@Component
public class TransactionAspect {
    @Pointcut("@within(transactional)")
    public void transactionalMethods(Transactional transactional) {
    }

    @Around(value = "transactionalMethods(transactional)", argNames = "jp, transactional")
    public Object logTransaction(ProceedingJoinPoint jp, Transactional transactional) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "anonymous";
        String callerClass = jp.getTarget().getClass().getName();
        String callerMethod = jp.getSignature().getName();
        String txId = UUID.randomUUID().toString();
        log.info("Transaction " + txId + " started by " + username + " in " + callerClass + "." + callerMethod);
        log.info("Transaction " + txId + " info: " + "propagation=" + transactional.propagation() + ", isolation=" + transactional.isolation() + ", readOnly=" + transactional.readOnly() + ", timeout=" + transactional.timeout());
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationImpl(txId));
        log.info("Method " + callerMethod + " called with args: " + parseArgs(jp.getArgs()));
        Object obj = jp.proceed();
        log.info("Method " + callerMethod + " returned with: " + parseArgs(obj));
        return obj;
    }

    String parseArgs(Object args) {
        return switch (args) {
            case null -> "no args";
            case AbstractEntity entity -> entity.getId().toString();
            case List<?> entities -> {
                StringBuilder sb = new StringBuilder();
                for (Object entity : entities) {
                    sb.append(((AbstractEntity) entity).getId()).append(", ");
                }
                yield sb.toString();
            }
            default -> args.toString();
        };
    }
}
