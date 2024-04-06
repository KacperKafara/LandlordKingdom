package pl.lodz.p.it.ssb2024.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.ssb2024.config.atomikos.AtomikosConfig;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAdmin;

@Configuration
@Import({DataSourceAdmin.class, AtomikosConfig.class})
public class RootConfig {
}
