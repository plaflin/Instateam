package com.teamtreehouse.instateam.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

// Java class to set up ORM and database configurations
@Configuration
@PropertySource("app.properties")
public class DataConfig {

    @Autowired
    private Environment env;

    // Function to set up ORM configuration
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(config);
        sessionFactory.setPackagesToScan(env.getProperty("instateam.entity.package"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    // Function to set up database configuration
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getProperty("instateam.db.driver"));
        dataSource.setUrl(env.getProperty("instateam.db.url"));
        dataSource.setUsername(env.getProperty("instateam.db.username"));
        dataSource.setPassword(env.getProperty("instateam.db.password"));
        return dataSource;
    }

}