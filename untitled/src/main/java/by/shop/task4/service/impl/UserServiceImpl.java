package by.shop.task4.service.impl;

import by.shop.task4.dao.impl.UserDaoImpl;
import by.shop.task4.entity.User;
import by.shop.task4.exception.DaoException;
import by.shop.task4.exception.ServiceException;
import by.shop.task4.service.UserService;
import by.shop.task4.util.PasswordEncoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static by.shop.task4.service.impl.param.ServiceParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private static UserServiceImpl instance;
    private final UserDaoImpl userDao;
    private final PasswordEncoder passwordEncoder;

    private UserServiceImpl() {
        this.userDao = UserDaoImpl.getInstance();
        this.passwordEncoder = PasswordEncoder.getInstance();
    }

    public static UserServiceImpl getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean authenticate(String username, String password) throws ServiceException {
        if (!validateUsername(username) || !validatePassword(password)) {
            return false;
        }
        try {
            Optional<User> userOptional = userDao.findByName(username.trim());
            if (userOptional.isEmpty()) {
                return false;
            }
            return passwordEncoder.matches(password, userOptional.get().getPasswordHash());
        } catch (DaoException e) {
            throw new ServiceException("Authentication failed", e);
        }
    }

    @Override
    public boolean register(String username, String email, String password) throws ServiceException {
        if (!validateUsername(username) || !validatePassword(password) || !validateEmail(email)) {
            return false;
        }
        try {
            if (userDao.userExists(username.trim())) {
                throw new ServiceException("Username already exists");
            }
            if (userDao.emailExists(email.trim())) {
                throw new ServiceException("Email already exists");
            }
            User user = new User(username.trim(), passwordEncoder.encode(password), email.trim());
            return userDao.insert(user);
        } catch (DaoException e) {
            throw new ServiceException("Registration failed", e);
        }
    }

    @Override
    public User getUserByName(String username) throws ServiceException {
        try {
            return userDao.findByName(username)
                    .orElseThrow(() -> new ServiceException("User not found"));
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user", e);
        }
    }

    @Override
    public List<User> getAllUsers(User currentUser) throws ServiceException {
        if (currentUser == null || !currentUser.isAdmin()) {
            throw new ServiceException("Access denied");
        }
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get users", e);
        }
    }

    @Override
    public boolean deleteUser(Long userId, User currentUser) throws ServiceException {
        if (currentUser == null || !currentUser.isAdmin()) {
            throw new ServiceException("Access denied");
        }
        if (userId == null || userId <= 0) {
            throw new ServiceException("Invalid user id");
        }
        if (currentUser.getId().equals(userId)) {
            throw new ServiceException("Cannot delete yourself");
        }
        try {
            return userDao.deleteById(userId);
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete user", e);
        }
    }

    private boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        String trimmed = username.trim();
        if (trimmed.length() < MIN_USERNAME_LENGTH || trimmed.length() > MAX_USERNAME_LENGTH) {
            return false;
        }
        return Pattern.matches(USERNAME_PATTERN, trimmed);
    }

    private boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    private boolean validateEmail(String email) {
        return email != null && Pattern.matches(EMAIL_PATTERN, email.trim());
    }
}
