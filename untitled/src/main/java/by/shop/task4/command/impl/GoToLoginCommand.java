package by.shop.task4.command.impl;

import by.shop.task4.command.Command;
import by.shop.task4.controller.router.Router;
import by.shop.task4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.shop.task4.command.impl.param.RequestParameter.LOGIN_PAGE;

public class GoToLoginCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return Router.forwardTo(LOGIN_PAGE);
    }
}
