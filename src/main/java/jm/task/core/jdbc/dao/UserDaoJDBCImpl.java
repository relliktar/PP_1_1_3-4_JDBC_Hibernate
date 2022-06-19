package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String DATABASES = "users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Util.statement.execute(
                    "CREATE TABLE " + DATABASES + " (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(30) NOT NULL, " +
                            "lastName VARCHAR(30) NOT NULL, " +
                            "age TINYINT NOT NULL)");
        } catch (SQLException ignored) {
        }
    }

    public void dropUsersTable() {
        try {
            Util.statement.execute("DROP TABLE " + DATABASES);
        } catch (SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String userSave = String.format("INSERT %s(name, lastName, age) VALUES ('%s', '%s', %d)",
                DATABASES, name, lastName, age);
        try {
            Util.statement.execute(userSave);
        } catch (SQLException ignored) {
        }
    }

    public void removeUserById(long id) {
        try {
            Util.statement.executeUpdate("DELETE FROM " + DATABASES + " WHERE id=" + id);
        } catch (SQLException ignored) {
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            ResultSet result = Util.statement.executeQuery("SELECT * FROM " + DATABASES);
            while (result.next()) {
                User user = new User(
                        result.getString("name"),
                        result.getString("lastName"),
                        result.getByte("age"));
                user.setId(result.getLong("id"));
                usersList.add(user);
            }
        } catch (SQLException ignored) {
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try {
            Util.statement.execute("DELETE FROM " + DATABASES);
        } catch (SQLException ignored) {
        }
    }
}
