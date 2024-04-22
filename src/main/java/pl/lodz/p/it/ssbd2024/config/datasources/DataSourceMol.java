package pl.lodz.p.it.ssbd2024.config.datasources;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.atomikos.jdbc.AtomikosNonXADataSourceBean;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.postgresql.xa.PGXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMol",
        basePackages = {"pl.lodz.p.it.ssbd2024.mol.repositories"}
)
@RequiredArgsConstructor
public class DataSourceMol {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Value("${driver_classname}")
    private String driverClassName;

    @Value("${url}")
    private String url;

    @Value("${default_transaction_isolation}")
    private int transactionIsolation;

    @Value("${db.mol.username}")
    private String username;

    @Value("${db.mol.password}")
    private String password;

    private AtomikosNonXADataSourceBean dataSource() {
        AtomikosNonXADataSourceBean dataSource = new AtomikosNonXADataSourceBean();
        dataSource.setDriverClassName(driverClassName);
        if(System.getenv("DATABASE_URL") != null) {
            url = System.getenv("DATABASE_URL");
        }
        dataSource.setUniqueResourceName("mol");
        dataSource.setUrl(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setDefaultIsolationLevel(transactionIsolation);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactoryMol() {
        AtomikosNonXADataSourceBean dataSource = dataSource();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setJtaDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02mol");
        em.setPackagesToScan("pl.lodz.p.it.ssbd2024.model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }
}
