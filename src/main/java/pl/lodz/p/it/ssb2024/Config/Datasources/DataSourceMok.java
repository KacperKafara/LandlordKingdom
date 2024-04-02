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
        basePackages = "pl.lodz.p.it.ssb2024.mok.Repositories"
)
public class DataSourceMok {

    private final JpaVendorAdapter jpaVendorAdapter;

    @Autowired
    public DataSourceMok(JpaVendorAdapter jpaVendorAdapter) {
        this.jpaVendorAdapter = jpaVendorAdapter;
    }

    private DataSource dataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/ssbd02");
        dataSource.setUsername("ssbd02mok");
        dataSource.setPassword("mok");
        dataSource.setDefaultTransactionIsolation(2);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        DataSource dataSource = dataSource();
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("ssbd02mok");
        em.setPackagesToScan("pl.lodz.p.it.ssb2024.Model.Users");
        em.setJpaVendorAdapter(jpaVendorAdapter);
        Properties properties = PublicProperties.getProperties();
        em.setJpaProperties(properties);
        em.afterPropertiesSet();
        return em.getObject();
    }
}
