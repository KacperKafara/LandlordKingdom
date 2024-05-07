package pl.lodz.p.it.ssbd2024.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@PropertySource("classpath:config.properties")
@PropertySource("classpath:app.properties")
@ComponentScan({
        "pl.lodz.p.it.ssbd2024.util",
        "pl.lodz.p.it.ssbd2024.services",
        "pl.lodz.p.it.ssbd2024.aspects",
        "pl.lodz.p.it.ssbd2024.mok.services",
        "pl.lodz.p.it.ssbd2024.mol.repositories",
        "pl.lodz.p.it.ssbd2024.mok.repositories",
})
@EnableAspectJAutoProxy
@EnableScheduling
public class ToolConfig {
    @Value("${threadPool.size}")
    private int threadPoolSize;

    @Bean
    public ResourceBundleMessageSource mailMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/mail/messages");
        return messageSource;
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(threadPoolSize);
        return taskScheduler;
    }
}
