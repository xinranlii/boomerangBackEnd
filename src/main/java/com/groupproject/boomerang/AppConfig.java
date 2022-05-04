package com.groupproject.boomerang;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean(name = "mapper")
    ObjectMapper mapper() {

        return new ObjectMapper();
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("com.groupproject.boomerang.model.pojo"); //.model 还是 存在pojo
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }



    @Bean("dataSource")
    public DataSource dataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
       // dataSource.setUrl("jdbc:mysql://laiproject-instance.codvnc8vwvtr.us-east-2.rds.amazonaws.com:3306/ecommerce");

        dataSource.setUrl("jdbc:mysql://laiproject-instance.csnfvyyeaiws.us-east-2.rds.amazonaws.com:3306/boomerang?createDatabaseIfNotExist=true&serverTimezone=UTC");
        dataSource.setUsername("admin");
        dataSource.setPassword("asdASD123");

        return dataSource;

    }

    private final Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop"); // "create-drop" 创建新表 -drop
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        //hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect");
        return hibernateProperties;
    }
}
