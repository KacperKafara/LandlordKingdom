package pl.lodz.p.it.ssb2024.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import pl.lodz.p.it.ssb2024.config.atomikos.AtomikosConfig;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAdmin;

@PropertySource("classpath:config.properties")
@Configuration
@Import({DataSourceAdmin.class, AtomikosConfig.class})
public class RootConfig {
}
