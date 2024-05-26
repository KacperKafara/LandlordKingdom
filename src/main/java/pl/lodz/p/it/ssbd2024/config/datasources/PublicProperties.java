package pl.lodz.p.it.ssbd2024.config.datasources;

import java.util.Properties;

public class PublicProperties {
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("jakarta.persistence.transactionType", "JTA");
        return properties;
    }
}
