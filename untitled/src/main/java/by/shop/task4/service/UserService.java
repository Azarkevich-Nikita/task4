package by.shop.task4.service;

import by.shop.task4.entity.User;
import by.shop.task4.exception.ServiceException;

import java.util.List;

public interface UserService {

    boolean authenticate(String username, String password) throws ServiceException;

    boolean register(String username, String email, String password) throws ServiceException;

    User getUserByName(String username) throws ServiceException;

    List<User> getAllUsers(User currentUser) throws ServiceException;

    boolean deleteUser(Long userId, User currentUser) throws ServiceException;
}
