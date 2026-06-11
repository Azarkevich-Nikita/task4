package by.shop.task4.dao;

import by.shop.task4.entity.User;
import by.shop.task4.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean insert(User user) throws DaoException;

    boolean deleteById(Long id) throws DaoException;

    List<User> findAll() throws DaoException;

    Optional<User> findByName(String username) throws DaoException;

    Optional<User> findById(Long id) throws DaoException;

    boolean userExists(String username) throws DaoException;

    boolean emailExists(String email) throws DaoException;
}
