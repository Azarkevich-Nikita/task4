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

public class UpdateItemCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateItemCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute(PARAM_USER) : null;
        if (currentUser == null) {
            return Router.forwardTo(LOGIN_PAGE);
        }

        try {
            Long id = Long.parseLong(request.getParameter(PARAM_ITEM_ID));
            Long price = Long.parseLong(request.getParameter(PARAM_ITEM_PRICE));
            Item item = new Item(
                    id,
                    request.getParameter(PARAM_ITEM_TITLE),
                    price,
                    request.getParameter(PARAM_ITEM_DESCRIPTION),
                    request.getParameter(PARAM_ITEM_MANUFACTURE_TEAM),
                    currentUser.getId()
            );

            ItemService itemService = ItemServiceImpl.getInstance();
            if (itemService.updateItem(item, currentUser)) {
                return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_items&message=Товар обновлён");
            }
            request.setAttribute(PARAM_ERROR, "Не удалось обновить товар или нет прав");
            request.setAttribute(PARAM_ITEM, item);
            return Router.forwardTo(EDIT_ITEM_PAGE);
        } catch (NumberFormatException e) {
            request.setAttribute(PARAM_ERROR, "Некорректные данные");
            return Router.forwardTo(EDIT_ITEM_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Failed to update item", e);
            request.setAttribute(PARAM_ERROR, e.getMessage());
            return Router.forwardTo(EDIT_ITEM_PAGE);
        }
    }
}
