package pl.lodz.p.it.ssbd2024.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({
        "pl.lodz.p.it.ssbd2024.mok.controllers",
        "pl.lodz.p.it.ssbd2024.exceptions.handlers"
})
public class WebConfig implements WebMvcConfigurer {
}
