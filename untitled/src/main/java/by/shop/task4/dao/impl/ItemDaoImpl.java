package by.shop.task4.dao.impl;

import by.shop.task4.connection.ConnectionPool;
import by.shop.task4.dao.AbstractDao;
import by.shop.task4.dao.ItemDao;
import by.shop.task4.entity.Item;
import by.shop.task4.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemDaoImpl extends AbstractDao<Item> implements ItemDao {
    private static final Logger LOGGER = LogManager.getLogger(ItemDaoImpl.class);
    private static ItemDaoImpl instance;

    private static final String ADD_ITEM_SQL =
            "INSERT INTO items (title, price, description, manufacture_team, owner_id) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_ITEMS_SQL =
            "SELECT id, title, price, description, manufacture_team, owner_id FROM items";
    private static final String GET_ITEM_BY_ID_SQL =
            "SELECT id, title, price, description, manufacture_team, owner_id FROM items WHERE id = ?";
    private static final String DELETE_ITEM_BY_ID_SQL = "DELETE FROM items WHERE id = ?";
    private static final String UPDATE_ITEM_SQL =
            "UPDATE items SET title = ?, price = ?, description = ?, manufacture_team = ?, owner_id = ? WHERE id = ?";
    private static final String EXISTS_BY_ID_SQL = "SELECT EXISTS (SELECT 1 FROM items WHERE id = ?)";

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        if (instance == null) {
            instance = new ItemDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Item item) throws DaoException {
        LOGGER.debug("Attempting to add item: {}", item.getTitle());
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(ADD_ITEM_SQL)) {
            statement.setString(1, item.getTitle());
            statement.setLong(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getManufactureTeam());
            statement.setLong(5, item.getOwnerId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to add item '{}'", item.getTitle(), e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteById(Long id) throws DaoException {
        LOGGER.debug("Deleting item by ID: {}", id);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ITEM_BY_ID_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to delete item with ID: {}", id, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public List<Item> findAll() throws DaoException {
        LOGGER.debug("Fetching all items");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_ALL_ITEMS_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            return getItemListFromResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error("Failed to fetch all items", e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public Optional<Item> findById(Long id) throws DaoException {
        LOGGER.debug("Fetching item by ID: {}", id);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(GET_ITEM_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(createItemFromResultSet(resultSet));
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to fetch item by ID: {}", id, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean update(Item item) throws DaoException {
        LOGGER.debug("Attempting to update item: {}", item.getTitle());
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM_SQL)) {
            statement.setString(1, item.getTitle());
            statement.setLong(2, item.getPrice());
            statement.setString(3, item.getDescription());
            statement.setString(4, item.getManufactureTeam());
            statement.setLong(5, item.getOwnerId());
            statement.setLong(6, item.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.error("Failed to update item '{}'", item.getTitle(), e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean existsById(Long id) throws DaoException {
        LOGGER.debug("Checking existence for item ID: {}", id);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement statement = connection.prepareStatement(EXISTS_BY_ID_SQL)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Database error while checking existence for item ID: {}", id, e);
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private Item createItemFromResultSet(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getLong("price"),
                resultSet.getString("description"),
                resultSet.getString("manufacture_team"),
                resultSet.getLong("owner_id")
        );
    }

    private List<Item> getItemListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()) {
            itemList.add(createItemFromResultSet(resultSet));
        }
        return itemList;
    }
}
