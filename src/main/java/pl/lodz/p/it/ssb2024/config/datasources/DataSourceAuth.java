package pl.lodz.p.it.ssb2024.config.datasources;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
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

    private DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(driverClassName);
        if(System.getenv("DATABASE_URL") != null) {
            url = System.getenv("DATABASE_URL");
        }
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDefaultTransactionIsolation(transactionIsolation);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryAuth() {
        DataSource dataSource = dataSource();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02auth");
        em.setPackagesToScan("pl.lodz.p.it.ssb2024.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }
}
