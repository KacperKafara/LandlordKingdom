package pl.lodz.p.it.ssb2024.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("pl.lodz.p.it.ssb2024")
@PropertySource("classpath:config.properties")
@EnableWebMvc
@PropertySource("classpath:app.properties")
public class WebConfig implements WebMvcConfigurer {
}
