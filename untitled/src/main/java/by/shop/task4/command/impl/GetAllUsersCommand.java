package by.shop.task4.command.impl;

import by.shop.task4.command.Command;
import by.shop.task4.controller.router.Router;
import by.shop.task4.entity.User;
import by.shop.task4.exception.CommandException;
import by.shop.task4.exception.ServiceException;
import by.shop.task4.service.UserService;
import by.shop.task4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.shop.task4.command.impl.param.RequestParameter.*;

public class GetAllUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetAllUsersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute(PARAM_USER) : null;
        if (currentUser == null) {
            return Router.forwardTo(LOGIN_PAGE);
        }

        String message = request.getParameter(PARAM_MESSAGE);
        String error = request.getParameter(PARAM_ERROR);
        if (message != null) {
            request.setAttribute(PARAM_MESSAGE, message);
        }
        if (error != null) {
            request.setAttribute(PARAM_ERROR, error);
        }

        try {
            UserService userService = UserServiceImpl.getInstance();
            List<User> users = userService.getAllUsers(currentUser);
            users.forEach(user -> user.setPasswordHash(null));
            request.setAttribute(PARAM_USER_LIST, users);
            request.setAttribute(PARAM_USER, currentUser);
            return Router.forwardTo(USERS_PAGE);
        } catch (ServiceException e) {
            LOGGER.warn("Access denied or failed to get users: {}", e.getMessage());
            return Router.redirectTo(request.getContextPath()
                    + "/controller?command=get_all_items&error=" + e.getMessage());
        }
    }
}
