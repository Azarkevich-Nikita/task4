package by.shop.task4.dao;

import by.shop.task4.entity.Item;
import by.shop.task4.exception.ConnectionPoolException;
import by.shop.task4.exception.DaoException;
import java.util.List;
import java.util.Optional;

public interface ItemDao {
    boolean add(Item item) throws DaoException, ConnectionPoolException;
    boolean deleteById(Long id) throws DaoException;
    List<Item> findAll() throws DaoException;
    Optional<Item> findById(Long id) throws DaoException;
    boolean update(Item item) throws DaoException;
    boolean existsById(Long id) throws DaoException;
}