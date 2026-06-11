package by.shop.task4.command.impl;

import by.shop.task4.command.Command;
import by.shop.task4.controller.router.Router;
import by.shop.task4.entity.User;
import by.shop.task4.exception.CommandException;
import by.shop.task4.exception.ServiceException;
import by.shop.task4.service.ItemService;
import by.shop.task4.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.shop.task4.command.impl.param.RequestParameter.*;

public class DeleteItemCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteItemCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute(PARAM_USER) : null;
        if (currentUser == null) {
            return Router.forwardTo(LOGIN_PAGE);
        }

        try {
            Long id = Long.parseLong(request.getParameter(PARAM_ITEM_ID));
            ItemService itemService = ItemServiceImpl.getInstance();
            if (itemService.deleteItem(id, currentUser)) {
                return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_items&message=Товар удалён");
            }
            return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_items&error=Не удалось удалить товар");
        } catch (NumberFormatException e) {
            return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_items&error=Некорректный ID");
        } catch (ServiceException e) {
            LOGGER.error("Failed to delete item", e);
            throw new CommandException(e);
        }
    }
}
