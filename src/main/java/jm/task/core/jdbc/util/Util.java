package jm.task.core.jdbc.util;

import lombok.Data;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static SessionFactory sessionFactory;

    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection open() {
        try {
          return DriverManager.getConnection(PropertiesUtil.get(URL_KEY),
                   PropertiesUtil.get(USERNAME_KEY),
                   PropertiesUtil.get(PASSWORD_KEY));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            // Создаем конфигурацию Hibernate
            System.out.println("Конфигурация для хибера");
            System.out.println("подгружаю все настройки");
            Configuration configuration = new Configuration();

            // Устанавливаем параметры подключения к базе данных
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:1234/postgres");
            configuration.setProperty("hibernate.connection.username", "postgres");
            configuration.setProperty("hibernate.connection.password", "Mi14062001");

            // Устанавливаем другие параметры конфигурации
            configuration.setProperty("hibernate.hbm2ddl.auto", "update"); // или "create", "validate" и т.д.
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            configuration.addAnnotatedClass(User.class);

            // Создаем SessionFactory
             StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder()
                       .applySettings(configuration.getProperties());
             sessionFactory = configuration.buildSessionFactory(registryBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Util() {
    }
}
