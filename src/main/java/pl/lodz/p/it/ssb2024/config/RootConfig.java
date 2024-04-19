package pl.lodz.p.it.ssb2024.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import pl.lodz.p.it.ssb2024.config.atomikos.AtomikosConfig;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAdmin;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAuth;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceMok;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceMol;

@Configuration
@PropertySource("classpath:config.properties")
@PropertySource("classpath:app.properties")
@ComponentScan({
        "pl.lodz.p.it.ssb2024.util",
        "pl.lodz.p.it.ssb2024.mok.services",
        "pl.lodz.p.it.ssb2024.mol.repositories",
        "pl.lodz.p.it.ssb2024.mok.repositories",
})
@Import({
        AtomikosConfig.class,
        DataSourceAdmin.class,
        DataSourceMok.class,
        DataSourceMol.class,
        DataSourceAuth.class,
})
public class RootConfig {
}
