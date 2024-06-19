package pl.lodz.p.it.ssbd2024.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({
        "pl.lodz.p.it.ssbd2024.controllers",
        "pl.lodz.p.it.ssbd2024.mok.controllers",
        "pl.lodz.p.it.ssbd2024.mol.controllers",
        "pl.lodz.p.it.ssbd2024.exceptions.handlers"
})
public class WebConfig implements WebMvcConfigurer {

    @Bean
    MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
