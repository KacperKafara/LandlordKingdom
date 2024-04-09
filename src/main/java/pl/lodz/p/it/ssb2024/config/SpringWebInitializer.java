package pl.lodz.p.it.ssb2024.config;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import pl.lodz.p.it.ssb2024.config.atomikos.AtomikosConfig;
import pl.lodz.p.it.ssb2024.config.datasources.DataSourceAdmin;
import pl.lodz.p.it.ssb2024.config.security.SecurityConfig;

public class SpringWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebConfig.class, SecurityConfig.class, DataSourceAdmin.class, AtomikosConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }


    @Override
    @NonNull
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
