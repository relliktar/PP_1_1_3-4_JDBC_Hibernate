package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3";
    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Properties property = new Properties();
            property.setProperty("hibernate.connection.url", URL);
            property.setProperty("hibernate.connection.username", USER_NAME);
            property.setProperty("hibernate.connection.password", PASSWORD);
            property.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            property.setProperty("dialect", "org.hibernate.dialect.MySQL5Dialect");
            sessionFactory = new Configuration()
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .addProperties(property)
                    .buildSessionFactory();
        }
        return sessionFactory;
    }

}
