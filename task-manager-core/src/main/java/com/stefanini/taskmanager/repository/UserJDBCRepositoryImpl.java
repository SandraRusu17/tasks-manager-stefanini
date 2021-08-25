package com.stefanini.taskmanager.repository;

import com.stefanini.taskmanager.entity.Task;
import com.stefanini.taskmanager.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserJDBCRepositoryImpl implements UserRepository {

    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DATABASE = "taskmanager";
    private final String USERNAME = "root";
    private final String PASSWORD = "mysqleight";

    private static final String DELETE_USER = "DELETE FROM users WHERE id=?";
    private static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE userName=?";
    private static final String FIND_ALL = "SELECT * FROM users ORDER BY id";


    public static UserJDBCRepositoryImpl INSTANCE;

    private UserJDBCRepositoryImpl() {
    }

    public static UserJDBCRepositoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserJDBCRepositoryImpl();
        }

        return INSTANCE;
    }

    private Connection getDBConnection() throws SQLException {
            return DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
    }

    @Override
    public int saveUser(User user) {
        int result = 0;
        try (Connection connection = getDBConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO users(firstName, lastName, userName) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUserName());
            result = ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {

                if (generatedKeys.next())
                    user.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public Optional<User> findByUsername(String username) {

//        User user = null;
        Optional<User> result;
        try (Connection connection = getDBConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_USERNAME);
             PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM tasks WHERE user_id = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                final User user = new User(rs.getLong("id"),
                                                       rs.getString("firstName"),
                                                       rs.getString("lastName"),
                                                       rs.getString("userName"));
                result = Optional.of(user);
                ps2.setLong(1, user.getId());

                try(ResultSet r2 = ps2.executeQuery()) {
                    while (r2.next()) {
                        Task task = new Task(r2.getString("title"),
                                r2.getString("description"));
                        task.setId(r2.getLong("id"));
                        user.addTask(task);
                    }
                }
                return result;
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = getDBConnection();
             PreparedStatement ps1 = connection.prepareStatement(FIND_ALL);
             PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM tasks WHERE user_id = ?")) {

            try (ResultSet r = ps1.executeQuery()) {
                while (r.next()) {
                    User user = new User(r.getLong("id"),
                            r.getString("firstName"),
                            r.getString("lastName"),
                            r.getString("userName"));
                    users.add(user);
                }
            }

            for (User user : users) {
                ps2.setLong(1, user.getId());
                try (ResultSet r2 = ps2.executeQuery()) {
                    while (r2.next()) {
                        Task task = new Task(r2.getString("title"),
                                            r2.getString("description"));
                        task.setId(r2.getLong("id"));
                        user.addTask(task);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public int deleteUserById(Long id) {
        try (Connection connection = getDBConnection();
             PreparedStatement ps1 = connection.prepareStatement(DELETE_USER)) {

            ps1.setLong(1, id);
            return ps1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
