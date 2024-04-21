package pl.lodz.p.it.ssb2024.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import pl.lodz.p.it.ssb2024.config.security.SecurityConfig;

public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    public SpringSecurityInitializer() {
        super(RootConfig.class, SecurityConfig.class, WebConfig.class);
    }
}