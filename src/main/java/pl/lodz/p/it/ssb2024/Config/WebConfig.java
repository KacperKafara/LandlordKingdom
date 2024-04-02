package pl.lodz.p.it.ssb2024.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("pl.lodz.p.it.ssb2024")
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
}
