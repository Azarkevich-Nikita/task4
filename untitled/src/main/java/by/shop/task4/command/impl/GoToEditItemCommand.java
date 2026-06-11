package by.shop.task4.command.impl;

import by.shop.task4.command.Command;
import by.shop.task4.controller.router.Router;
import by.shop.task4.entity.Item;
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

public class GoToEditItemCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToEditItemCommand.class);

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
            Item item = itemService.findItemById(id).orElse(null);
            if (item == null) {
                request.setAttribute(PARAM_ERROR, "Товар не найден");
                return Router.forwardTo(ITEMS_PAGE);
            }
            if (!currentUser.isAdmin() && !currentUser.getId().equals(item.getOwnerId())) {
                return Router.redirectTo(request.getContextPath()
                        + "/controller?command=get_all_items&error=Нет прав на редактирование");
            }
            request.setAttribute(PARAM_ITEM, item);
            return Router.forwardTo(EDIT_ITEM_PAGE);
        } catch (NumberFormatException e) {
            request.setAttribute(PARAM_ERROR, "Некорректный ID");
            return Router.forwardTo(ITEMS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Failed to load item for edit", e);
            throw new CommandException(e);
        }
    }
}
