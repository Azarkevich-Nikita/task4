package by.shop.task4.command;

import by.shop.task4.controller.router.Router;
import by.shop.task4.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
