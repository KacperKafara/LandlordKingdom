package pl.lodz.p.it.ssb2024.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@PropertySource("classpath:app.properties")
@ComponentScan({
        "pl.lodz.p.it.ssb2024.util",
        "pl.lodz.p.it.ssb2024.mok.services",
        "pl.lodz.p.it.ssb2024.mol.repositories",
        "pl.lodz.p.it.ssb2024.mok.repositories",
})
public class ToolConfig {
}
