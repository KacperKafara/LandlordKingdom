package pl.lodz.p.it.ssbd2024.config.datasources;

import com.atomikos.jdbc.AtomikosNonXADataSourceBean;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSourceAdmin {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Value("${db.driverClassName}")
    private String driverClassName;

    @Value("${db.url}")
    private String url;

    @Value("${db.defaultTransactionIsolation}")
    private int transactionIsolation;

    @Value("${db.admin.username}")
    private String username;

    @Value("${db.admin.password}")
    private String password;

    private AtomikosNonXADataSourceBean dataSource() {
        AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUniqueResourceName("admin");
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDefaultIsolationLevel(transactionIsolation);
        dataSource.setMaxPoolSize(20);
        dataSource.setBorrowConnectionTimeout(60);
        dataSource.setLocalTransactionMode(true);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryAdmin() {
        AtomikosNonXADataSourceBean dataSource = dataSource();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02admin");
        em.setPackagesToScan("pl.lodz.p.it.ssbd2024.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("jakarta.persistence.sql-load-script-source", "init.sql init2.sql");
        properties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.schema.internal.script.MultiLineSqlScriptExtractor");
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }
}
