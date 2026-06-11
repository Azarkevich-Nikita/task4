package by.shop.task4.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static by.shop.task4.connection.param.ConnectionParameter.*;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final int MAX_CONNECTIONS_CAPACITY = 10;
    private static final Lock lock = new ReentrantLock();
    private static final String URL = "jdbc:postgresql://localhost:5432/f1Shop";
    private static ConnectionPool instance;
    private final BlockingDeque<Connection> freeConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS_CAPACITY);
    private final BlockingDeque<Connection> usedConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS_CAPACITY);

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            LOGGER.fatal("Failed to register Driver");
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        LOGGER.info("Creating connection");
        Properties prop = new Properties();
        prop.put(PARAM_USER, PARAM_POSTGRES);
        prop.put(PARAM_PASSWORD, PARAM_ROOT);
        for (int i = 0; i < MAX_CONNECTIONS_CAPACITY; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, prop);
            } catch (SQLException e) {
                LOGGER.fatal("ConnectionPool failed");
                throw new ExceptionInInitializerError(e);
            }
            LOGGER.info("Creating connection number = {}", i + 1);
            freeConnections.add(connection);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void destroyPool() {
        for(int i = 0; i < MAX_CONNECTIONS_CAPACITY; i++){
            try {
                freeConnections.take().close();
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deregisterDriver() {
        try{
            DriverManager.deregisterDriver(DriverManager.getDriver(URL));
        } catch (SQLException e) {
            LOGGER.error("Failed to deregister Driver");
        }
    }
}