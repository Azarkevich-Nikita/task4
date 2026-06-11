package by.shop.task4.command.impl;

import by.shop.task4.command.Command;
import by.shop.task4.controller.router.Router;
import by.shop.task4.exception.CommandException;
import by.shop.task4.exception.ServiceException;
import by.shop.task4.service.UserService;
import by.shop.task4.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.shop.task4.command.impl.param.RequestParameter.*;

public class RegisterCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String username = request.getParameter(PARAM_USERNAME);
        String email = request.getParameter(PARAM_EMAIL);
        String password = request.getParameter(PARAM_PASSWORD);
        UserService userService = UserServiceImpl.getInstance();

        try {
            if (userService.register(username, email, password)) {
                request.setAttribute(PARAM_MESSAGE, "Регистрация успешна. Войдите в аккаунт.");
                return Router.forwardTo(LOGIN_PAGE);
            }
            request.setAttribute(PARAM_ERROR,
                    "Некорректные данные. Логин: 3-20 символов, пароль: 8+ символов с цифрой, заглавной, строчной и спецсимволом (@#$%^&+=)");
            return Router.forwardTo(REGISTRATION_PAGE);
        } catch (ServiceException e) {
            LOGGER.warn("Registration failed: {}", e.getMessage());
            request.setAttribute(PARAM_ERROR, e.getMessage());
            return Router.forwardTo(REGISTRATION_PAGE);
        }
    }
}
