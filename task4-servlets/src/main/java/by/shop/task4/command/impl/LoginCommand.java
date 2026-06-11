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

import static by.shop.task4.command.impl.param.RequestParameter.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String username = request.getParameter(PARAM_USERNAME);
        String password = request.getParameter(PARAM_PASSWORD);
        UserService userService = UserServiceImpl.getInstance();

        try {
            if (userService.authenticate(username, password)) {
                User user = userService.getUserByName(username.trim());
                user.setPasswordHash(null);
                HttpSession session = request.getSession();
                session.setAttribute(PARAM_USER, user);
                LOGGER.info("User {} logged in", username);
                return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_items");
            }
            request.setAttribute(PARAM_ERROR, "Неверный логин или пароль");
            return Router.forwardTo(LOGIN_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Login failed", e);
            throw new CommandException(e);
        }
    }
}
