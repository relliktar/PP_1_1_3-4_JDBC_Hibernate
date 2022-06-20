package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final String TABLE = "users";
    private Connection connection = Util.getConnection();
    private Statement statement;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
            statement = connection.createStatement();
            statement.execute(
                    "CREATE TABLE " + TABLE + " (" +
                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(30) NOT NULL, " +
                            "lastName VARCHAR(30) NOT NULL, " +
                            "age TINYINT NOT NULL)");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("createUsersTable " + e.getMessage());
            try {
                connection.rollback(savepoint);
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void dropUsersTable() {
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
            statement = connection.createStatement();
            statement.execute("DROP TABLE " + TABLE);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("dropUsersTable " + e.getMessage());
            try {
                connection.rollback(savepoint);
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        PreparedStatement ps = null;
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
            statement = connection.createStatement();
            String sql = "INSERT " + TABLE + "(name, lastName, age) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("saveUser " + e.getMessage());
            try {
                connection.rollback(savepoint);
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException ignored) {
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public void removeUserById(long id) {
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + TABLE + " WHERE id=" + id);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("removeUserById " + e.getMessage());
            try {
                connection.rollback(savepoint);
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        ResultSet result = null;
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM " + TABLE);
            while (result.next()) {
                User user = new User(
                        result.getString("name"),
                        result.getString("lastName"),
                        result.getByte("age"));
                user.setId(result.getLong("id"));
                usersList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("getAllUsers " + e.getMessage());
            try {
                connection.rollback(savepoint);
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (result != null) result.close();
            } catch (SQLException ignored) {
            }
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
        }
        return usersList;
    }

    public void cleanUsersTable() {
        Savepoint savepoint = null;
        try {
            connection = Util.getConnection();
            savepoint = connection.setSavepoint();
            statement = connection.createStatement();
            statement.execute("DELETE FROM " + TABLE);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("cleanUsersTable " + e.getMessage());
            try {
                connection.rollback(savepoint);
            } catch (SQLException ignored) {
            }
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ignored) {
            }
        }
    }
}
