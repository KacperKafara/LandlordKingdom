package pl.lodz.p.it.ssb2024.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.lodz.p.it.ssb2024.Config.Atomikos.AtomikosConfig;
import pl.lodz.p.it.ssb2024.Config.Datasources.DataSourceAdmin;

@Configuration
@Import({DataSourceAdmin.class, AtomikosConfig.class})
public class RootConfig {
}
