package com.example.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseManager {
    public static final String MOD_ID = "template-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static SessionFactory sessionFactory;

    public static void initialize(String jdbcUrl, String username, String password) {
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", jdbcUrl);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.addAnnotatedClass(MessageEntity.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}