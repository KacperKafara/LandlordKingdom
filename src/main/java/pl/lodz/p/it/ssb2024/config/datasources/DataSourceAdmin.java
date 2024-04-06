package pl.lodz.p.it.ssb2024.config.datasources;

import jakarta.persistence.EntityManagerFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
public class DataSourceAdmin {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    public DataSourceAdmin(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    private DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/ssbd02");
        dataSource.setUsername("ssbd02admin");
        dataSource.setPassword("admin");
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(1);
        dataSource.setMaxIdle(10);
        dataSource.setDefaultTransactionIsolation(2);
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
