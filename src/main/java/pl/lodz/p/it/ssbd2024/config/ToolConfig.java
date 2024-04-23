package pl.lodz.p.it.ssbd2024.config;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.transaction.support.SimpleTransactionScope;

import java.util.Map;

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
public class ToolConfig {
    @Bean
    public CustomScopeConfigurer customScope() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        Map<String, Object> scopes = Map.of("transaction", new SimpleTransactionScope());
        customScopeConfigurer.setScopes(scopes);
        return customScopeConfigurer;
    }
}
