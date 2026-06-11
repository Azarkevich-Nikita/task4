package by.shop.task4.command.impl;

import by.shop.task4.command.Command;
import by.shop.task4.controller.router.Router;
import by.shop.task4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

import static by.shop.task4.command.impl.param.RequestParameter.REGISTRATION_PAGE;

public class GoToRegisterCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return Router.forwardTo(REGISTRATION_PAGE);
    }
}
