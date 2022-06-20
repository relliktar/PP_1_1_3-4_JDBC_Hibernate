package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@SuppressWarnings("unchecked")
public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sessionFactory;
    private final String TABLE = "users";
    private Session session;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30) NOT NULL, " +
                "lastName VARCHAR(30) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE " + TABLE).executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format("INSERT %s(name, lastName, age) VALUES ('%s', '%s', '%d')",
                TABLE, name, lastName, age);
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM " + TABLE + " WHERE id=" + id;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            users = (List<User>) session.createSQLQuery("SELECT * FROM " + TABLE)
                    .addEntity(User.class)
                    .list();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM " + TABLE).executeUpdate();
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}
