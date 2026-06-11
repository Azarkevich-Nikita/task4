package by.shop.task4.service;

import by.shop.task4.entity.Item;
import by.shop.task4.entity.User;
import by.shop.task4.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    boolean addItem(String title, Long price, String description, String manufactureTeam, Long ownerId)
            throws ServiceException;

    boolean deleteItem(Long id, User currentUser) throws ServiceException;

    List<Item> findAllItems() throws ServiceException;

    Optional<Item> findItemById(Long id) throws ServiceException;

    boolean updateItem(Item item, User currentUser) throws ServiceException;
}
