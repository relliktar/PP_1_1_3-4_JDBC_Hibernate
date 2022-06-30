package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl usi = new UserServiceImpl();
        usi.createUsersTable();
        List<User> users = new ArrayList<>(Arrays.asList(
                new User("Tyler", "Durden", (byte) 25),
                new User("Marla", "Singer", (byte) 22),
                new User("Robert", "Paulson", (byte) 35),
                new User("Angel", "Face", (byte) 20)
        ));
        users.forEach(user -> usi.saveUser(user.getName(), user.getLastName(), user.getAge()));
        usi.removeUserById(4);
        List<User> usersBD = usi.getAllUsers();
        usersBD.forEach(System.out::println);
        usi.cleanUsersTable();
        usi.dropUsersTable();
    }
}
