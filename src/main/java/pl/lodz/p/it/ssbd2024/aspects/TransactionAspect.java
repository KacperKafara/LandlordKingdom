package pl.lodz.p.it.ssbd2024.aspects;

import jakarta.annotation.Resource;
import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.UUID;

@Log
@Aspect
@Component
public class TransactionAspect {
    @Pointcut("@annotation(pl.lodz.p.it.ssbd2024.aspects.TxTracked)")
    public void serviceMethods() {
    }

    @Around("serviceMethods()")
    public Object logTransaction(ProceedingJoinPoint jp) throws Throwable {
        UUID id = UUID.randomUUID();
        String methodName = jp.getSignature().getName();
        log.info("Method " + id + " called");
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationImpl(id.toString()));
        Object obj = jp.proceed();
        log.info("Method " + methodName + " returned");
        return obj;
    }
}
