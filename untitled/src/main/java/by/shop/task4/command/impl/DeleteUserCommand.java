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

public class DeleteUserCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute(PARAM_USER) : null;
        if (currentUser == null) {
            return Router.forwardTo(LOGIN_PAGE);
        }

        try {
            Long userId = Long.parseLong(request.getParameter(PARAM_USER_ID));
            UserService userService = UserServiceImpl.getInstance();
            if (userService.deleteUser(userId, currentUser)) {
                return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_users&message=Пользователь удалён");
            }
            return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_users&error=Не удалось удалить пользователя");
        } catch (NumberFormatException e) {
            return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_users&error=Некорректный ID");
        } catch (ServiceException e) {
            LOGGER.error("Failed to delete user", e);
            return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_users&error=" + e.getMessage());
        }
    }
}
