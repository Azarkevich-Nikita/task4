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

import java.util.List;

import static by.shop.task4.command.impl.param.RequestParameter.*;

public class GetAllItemsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GetAllItemsCommand.class);

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
            ItemService itemService = ItemServiceImpl.getInstance();
            List<Item> items = itemService.findAllItems();
            request.setAttribute(PARAM_ITEM_LIST, items);
            request.setAttribute(PARAM_USER, currentUser);
            LOGGER.debug("Fetched {} items", items.size());
            return Router.forwardTo(ITEMS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Failed to fetch items list", e);
            throw new CommandException(e);
        }
    }
}
