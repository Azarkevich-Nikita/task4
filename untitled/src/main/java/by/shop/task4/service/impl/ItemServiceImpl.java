package by.shop.task4.service.impl;

import by.shop.task4.dao.impl.ItemDaoImpl;
import by.shop.task4.entity.Item;
import by.shop.task4.entity.User;
import by.shop.task4.exception.DaoException;
import by.shop.task4.exception.ServiceException;
import by.shop.task4.service.ItemService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {
    private static final Logger LOGGER = LogManager.getLogger(ItemServiceImpl.class);
    private static ItemServiceImpl instance;
    private final ItemDaoImpl itemDao;

    private ItemServiceImpl() {
        this.itemDao = ItemDaoImpl.getInstance();
    }

    public static ItemServiceImpl getInstance() {
        if (instance == null) {
            instance = new ItemServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addItem(String title, Long price, String description, String manufactureTeam, Long ownerId)
            throws ServiceException {
        Item item = new Item(title, price, description, manufactureTeam, ownerId);
        validateItem(item);
        try {
            return itemDao.add(item);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add item", e);
        }
    }

    @Override
    public boolean deleteItem(Long id, User currentUser) throws ServiceException {
        validateId(id);
        if (currentUser == null) {
            throw new ServiceException("User is not authenticated");
        }
        try {
            Optional<Item> itemOptional = itemDao.findById(id);
            if (itemOptional.isEmpty()) {
                return false;
            }
            Item item = itemOptional.get();
            if (!currentUser.isAdmin() && !currentUser.getId().equals(item.getOwnerId())) {
                LOGGER.warn("User {} has no permission to delete item {}", currentUser.getId(), id);
                return false;
            }
            return itemDao.deleteById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete item", e);
        }
    }

    @Override
    public List<Item> findAllItems() throws ServiceException {
        try {
            return itemDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to find items list", e);
        }
    }

    @Override
    public Optional<Item> findItemById(Long id) throws ServiceException {
        validateId(id);
        try {
            return itemDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to fetch item", e);
        }
    }

    @Override
    public boolean updateItem(Item item, User currentUser) throws ServiceException {
        validateItem(item);
        validateId(item.getId());
        if (currentUser == null) {
            throw new ServiceException("User is not authenticated");
        }
        try {
            Optional<Item> existingOptional = itemDao.findById(item.getId());
            if (existingOptional.isEmpty()) {
                return false;
            }
            Item existing = existingOptional.get();
            if (!currentUser.isAdmin() && !currentUser.getId().equals(existing.getOwnerId())) {
                LOGGER.warn("User {} has no permission to update item {}", currentUser.getId(), item.getId());
                return false;
            }
            item.setOwnerId(existing.getOwnerId());
            return itemDao.update(item);
        } catch (DaoException e) {
            throw new ServiceException("Failed to update item", e);
        }
    }

    private void validateItem(Item item) throws ServiceException {
        if (item == null) {
            throw new ServiceException("Item cannot be null");
        }
        if (item.getTitle() == null || item.getTitle().trim().isEmpty()) {
            throw new ServiceException("Item title cannot be empty");
        }
        if (item.getTitle().length() > 200) {
            throw new ServiceException("Item title is too long (max 200 characters)");
        }
        if (item.getPrice() == null || item.getPrice() <= 0) {
            throw new ServiceException("Item price must be greater than 0");
        }
        if (item.getManufactureTeam() == null || item.getManufactureTeam().trim().isEmpty()) {
            throw new ServiceException("Manufacture team cannot be empty");
        }
        if (item.getManufactureTeam().length() > 100) {
            throw new ServiceException("Manufacture team is too long (max 100 characters)");
        }
        if (item.getDescription() != null && item.getDescription().length() > 1000) {
            throw new ServiceException("Item description is too long (max 1000 characters)");
        }
        if (item.getOwnerId() == null || item.getOwnerId() <= 0) {
            throw new ServiceException("Item owner is required");
        }
    }

    private void validateId(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("Invalid item ID: must be a positive number");
        }
    }
}
