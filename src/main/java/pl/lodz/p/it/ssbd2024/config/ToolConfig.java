package pl.lodz.p.it.ssbd2024.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@PropertySource("classpath:config.properties")
@PropertySource("classpath:app.properties")
@ComponentScan({
        "pl.lodz.p.it.ssbd2024.util",
        "pl.lodz.p.it.ssbd2024.services",
        "pl.lodz.p.it.ssbd2024.mok.services",
        "pl.lodz.p.it.ssbd2024.mol.repositories",
        "pl.lodz.p.it.ssbd2024.mok.repositories",
})
public class ToolConfig {
    @Bean
    public ResourceBundleMessageSource mailMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/mail/messages");
        return messageSource;
    }
}
