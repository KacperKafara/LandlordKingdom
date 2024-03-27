package pl.lodz.p.it.ssb2024.Config.Datasources;

import java.util.Properties;

public class PublicProperties {
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("javax.persistence.transactionType", "JTA");
        return properties;
    }
}
