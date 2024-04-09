package pl.lodz.p.it.ssb2024.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import pl.lodz.p.it.ssb2024.config.atomikos.AtomikosConfig;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAdmin;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAuth;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceMok;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceMol;
import pl.lodz.p.it.ssb2024.config.security.SecurityConfig;

@PropertySource("classpath:config.properties")
@Configuration
@PropertySource("classpath:app.properties")
public class RootConfig {
}
