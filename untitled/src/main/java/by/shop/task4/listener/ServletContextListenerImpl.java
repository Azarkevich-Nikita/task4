package by.shop.task4.listener;

import by.shop.task4.connection.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ServletContextListenerImpl.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
        LOGGER.info("Application started, connection pool initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
        ConnectionPool.getInstance().deregisterDriver();
        LOGGER.info("Application stopped, connection pool destroyed");
    }
}
