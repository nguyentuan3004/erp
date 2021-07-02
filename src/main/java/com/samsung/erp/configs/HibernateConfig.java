package com.samsung.erp.configs;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
//@EnableTransactionManagement
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
        factory.setDataSource(dataSource());
        factory.setHibernateProperties(getProps());
        factory.setPackagesToScan("com.samsung.erp.entities");
//        factory.setPhysicalNamingStrategy(new PhysicalNamingStrategy());

        return factory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/erp");
        dataSource.setUsername("root");
        dataSource.setPassword("");

        return dataSource;
    }

    // dataSource.setPassword(env.getProperty("hibernate.connection.password"));

    public Properties getProps() {
        Properties props = new Properties();
        props.setProperty(AvailableSettings.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
        props.setProperty(AvailableSettings.SHOW_SQL, "true");
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        return props;
    }
}
