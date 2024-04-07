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
public class DataSourceAdmin {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Value("${driver_classname}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${transaction_isolation}")
    private int transactionIsolation;

    @Value("${db.admin.username}")
    private String username;

    @Value("${db.admin.password}")
    private String password;

    private DataSource dataSource() {
            DataSource dataSource = new DataSource();
            dataSource.setDriverClassName(driverClassName);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            dataSource.setInitialSize(1);
            dataSource.setMaxActive(1);
            dataSource.setMaxIdle(10);
            dataSource.setDefaultTransactionIsolation(transactionIsolation);
            return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        DataSource dataSource = dataSource();

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02admin");
        em.setPackagesToScan("pl.lodz.p.it.ssb2024.Model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("javax.persistence.sql-load-script-source", "init.sql");
        em.setJpaProperties(properties);
        em.afterPropertiesSet();

        return em.getObject();
    }
}
