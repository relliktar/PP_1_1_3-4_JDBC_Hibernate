package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3";
    public static Statement statement;
    public static Connection connection;

    static {
        try{
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    static {
        try{
            statement = connection.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
