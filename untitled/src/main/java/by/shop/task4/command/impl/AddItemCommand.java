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

public class AddItemCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddItemCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(false);
        User currentUser = session != null ? (User) session.getAttribute(PARAM_USER) : null;
        if (currentUser == null) {
            return Router.forwardTo(LOGIN_PAGE);
        }

        String title = request.getParameter(PARAM_ITEM_TITLE);
        String priceStr = request.getParameter(PARAM_ITEM_PRICE);
        String description = request.getParameter(PARAM_ITEM_DESCRIPTION);
        String manufactureTeam = request.getParameter(PARAM_ITEM_MANUFACTURE_TEAM);

        try {
            Long price = Long.parseLong(priceStr);
            ItemService itemService = ItemServiceImpl.getInstance();
            if (itemService.addItem(title, price, description, manufactureTeam, currentUser.getId())) {
                return Router.redirectTo(request.getContextPath() + "/controller?command=get_all_items&message=Товар добавлен");
            }
            request.setAttribute(PARAM_ERROR, "Не удалось добавить товар");
            return Router.forwardTo(ADD_ITEM_PAGE);
        } catch (NumberFormatException e) {
            request.setAttribute(PARAM_ERROR, "Некорректная цена");
            return Router.forwardTo(ADD_ITEM_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("Failed to add item", e);
            request.setAttribute(PARAM_ERROR, e.getMessage());
            return Router.forwardTo(ADD_ITEM_PAGE);
        }
    }
}
