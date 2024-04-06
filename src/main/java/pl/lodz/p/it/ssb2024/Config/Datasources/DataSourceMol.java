package pl.lodz.p.it.ssb2024.Config.Datasources;

import jakarta.persistence.EntityManagerFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = "pl.lodz.p.it.ssb2024.mol.Repositories"
)
public class DataSourceMol {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    public DataSourceMol(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    private DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://db:5432/ssbd02");
        dataSource.setUsername("ssbd02mol");
        dataSource.setPassword("molP@ssw0rd");
        dataSource.setDefaultTransactionIsolation(2);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        DataSource dataSource = dataSource();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02mol");
        em.setPackagesToScan("pl.lodz.p.it.ssb2024.Model");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }
}
