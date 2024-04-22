package pl.lodz.p.it.ssbd2024.config.datasources;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.AtomikosNonXADataSourceBean;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.postgresql.xa.PGXADataSource;
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

    @Value("${default_transaction_isolation}")
    private int transactionIsolation;

    @Value("${db.admin.username}")
    private String username;

    @Value("${db.admin.password}")
    private String password;

//    private AtomikosNonXADataSourceBean dataSource() {
//        AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
//        dataSource.setDriverClassName(driverClassName);
//        if(System.getenv("DATABASE_URL") != null) {
//            url = System.getenv("DATABASE_URL");
//        }
//        dataSource.setUniqueResourceName("admin");
//        dataSource.setUrl(url);
//        dataSource.setUser(username);
//        dataSource.setPassword(password);
//        dataSource.setDefaultIsolationLevel(transactionIsolation);
//        dataSource.setMaxIdleTime(10);
//        dataSource.setMinPoolSize(0);
//        dataSource.setMaxPoolSize(1);
//        return dataSource;
//    }

    private AtomikosDataSourceBean dataSource() {
        PGXADataSource PGDataSource = new PGXADataSource();
        if(System.getenv("DATABASE_URL") != null) {
            url = System.getenv("DATABASE_URL");
        }
        PGDataSource.setUrl(url);
        PGDataSource.setUser(username);
        PGDataSource.setPassword(password);

        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setXaDataSource(PGDataSource);
        dataSource.setUniqueResourceName("admin");
        dataSource.setTestQuery("SELECT 1");
        dataSource.setMaxPoolSize(1);
        dataSource.setMinPoolSize(0);
        dataSource.setMaxIdleTime(10);
        dataSource.setDefaultIsolationLevel(transactionIsolation);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryAdmin() {
        AtomikosDataSourceBean dataSource = dataSource();

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02admin");
        em.setPackagesToScan("pl.lodz.p.it.ssbd2024.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
//        em.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);
        Properties properties = PublicProperties.getProperties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("javax.persistence.sql-load-script-source", "init.sql");
        em.setJpaProperties(properties);
        em.afterPropertiesSet();

        return em.getObject();
    }
}
