package pl.lodz.p.it.ssbd2024.config.datasources;

import com.atomikos.jdbc.AtomikosNonXADataSourceBean;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMok",
        basePackages = {"pl.lodz.p.it.ssbd2024.mok.authRepositories"}
)
@RequiredArgsConstructor
public class DataSourceAuth {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Value("${driver_classname}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${default_transaction_isolation}")
    private int transactionIsolation;

    @Value("${db.auth.username}")
    private String username;

    @Value("${db.auth.password}")
    private String password;

    private AtomikosNonXADataSourceBean dataSource() {
        AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUniqueResourceName("auth");
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDefaultIsolationLevel(transactionIsolation);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryAuth() {
        AtomikosNonXADataSourceBean dataSource = dataSource();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02auth");
        em.setPackagesToScan("pl.lodz.p.it.ssbd2024.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }
}
