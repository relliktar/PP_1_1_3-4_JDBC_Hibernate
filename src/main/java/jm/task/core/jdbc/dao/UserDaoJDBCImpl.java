package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String TABLE = "users";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            Util.statement.execute(
                    "CREATE TABLE " + TABLE + " (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(30) NOT NULL, " +
                            "lastName VARCHAR(30) NOT NULL, " +
                            "age TINYINT NOT NULL)");
        } catch (SQLException ignored) {
        }
    }

    public void dropUsersTable() {
        try {
            Util.statement.execute("DROP TABLE " + TABLE);
        } catch (SQLException ignored) {
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            String sql = "INSERT " + TABLE + "(name, lastName, age) VALUES (?, ?, ?)";
            PreparedStatement ps = Util.connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
        } catch (SQLException ignored) {
        }
    }

    public void removeUserById(long id) {
        try {
            Util.statement.executeUpdate("DELETE FROM " + TABLE + " WHERE id=" + id);
        } catch (SQLException ignored) {
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try {
            ResultSet result = Util.statement.executeQuery("SELECT * FROM " + TABLE);
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
            Util.statement.execute("DELETE FROM " + TABLE);
        } catch (SQLException ignored) {
        }
    }
}
