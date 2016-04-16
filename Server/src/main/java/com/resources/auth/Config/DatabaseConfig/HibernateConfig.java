package com.resources.auth.Config.DatabaseConfig;

import java.util.Properties;

import javax.sql.DataSource;

import com.resources.auth.Database.Client.Client;
import com.resources.auth.Database.Client.ClientDAO;
import com.resources.auth.Database.Client.ClientDAOSec;
import com.resources.auth.Database.Users.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by tanvd on 13.12.15.
 */
@Configuration
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
public class HibernateConfig {
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        //Using basic datasource with supply of connection pool
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:src/main/DataBase/crew.db");
        return dataSource;
    }


    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "com.resources.auth.Database.Dialect.SQLiteDialect");
        //properties.put("hibernate.current_session_context_class", "thread");
        //properties.put("hibernate.show_sql", "true");
        //FIXME In production we need to add here ddl scheme and disable auto
        properties.put("hibernate.hbm2ddl.auto", "create");
        return properties;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) {
        LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
        sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.addAnnotatedClasses(User.class, UserAuthority.class);
        sessionBuilder.addAnnotatedClasses(Client.class);
        sessionBuilder.scanPackages("com.resources.auth.Database");
        return sessionBuilder.buildSessionFactory();
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Autowired
    @DependsOn("transactionManager")
    @Bean(name = "userService")
    public UserDAO getUserDao(SessionFactory sessionFactory) {
        UserDAO userService = new UserDAO();
        userService.setSessionFactory(sessionFactory);
        return userService;
    }

    @Autowired
    @Bean(name = "userServiceSec")
    public UserDAOSec getUserDaoSec(UserDAO userService) {
        UserDAOSec userServiceSec = new UserDAOSec();
        userServiceSec.setUserService(userService);
        return userServiceSec;
    }

    @Autowired
    @DependsOn("transactionManager")
    @Bean(name = "clientService")
    public ClientDAO getClientDao(SessionFactory sessionFactory) {
        ClientDAO clientService = new ClientDAO();
        clientService.setSessionFactory(sessionFactory);
        return clientService;
    }

    @Autowired
    @Bean(name = "clientServiceSec")
    public ClientDAOSec getClientDaoSec(ClientDAO clientService) {
        ClientDAOSec clientServiceSec = new ClientDAOSec();
        clientServiceSec.setClientService(clientService);
        return clientServiceSec;
    }

    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder getPasswordEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder;
    }

}