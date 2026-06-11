package by.shop.task4.dao.impl;

import by.shop.task4.connection.ConnectionPool;
import by.shop.task4.dao.UserDao;
import by.shop.task4.entity.Role;
import by.shop.task4.entity.User;
import by.shop.task4.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static UserDaoImpl instance;

    private static final String INSERT_USER =
            "INSERT INTO users (username, password_hash, email, role) VALUES (?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS =
            "SELECT id, username, password_hash, email, role, created_at FROM users";
    private static final String FIND_BY_NAME =
            "SELECT id, username, password_hash, email, role, created_at FROM users WHERE username = ?";
    private static final String FIND_BY_ID =
            "SELECT id, username, password_hash, email, role, created_at FROM users WHERE id = ?";
    private static final String USER_EXISTS = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)";
    private static final String EMAIL_EXISTS = "SELECT EXISTS (SELECT 1 FROM users WHERE email = ?)";

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPasswordHash());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole().name());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to insert user {}", user.getUsername(), e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete user {}", id, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(mapUser(resultSet));
            }
            return users;
        } catch (SQLException e) {
            LOGGER.error("Failed to fetch users", e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> findByName(String username) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapUser(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find user {}", username, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapUser(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to find user {}", id, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean userExists(String username) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(USER_EXISTS)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to check user existence {}", username, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean emailExists(String email) throws DaoException {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(EMAIL_EXISTS)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to check email existence {}", email, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private User mapUser(ResultSet resultSet) throws SQLException {
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password_hash"),
                resultSet.getString("email"),
                Role.valueOf(resultSet.getString("role")),
                createdAt != null ? new java.util.Date(createdAt.getTime()) : null
        );
    }
}
