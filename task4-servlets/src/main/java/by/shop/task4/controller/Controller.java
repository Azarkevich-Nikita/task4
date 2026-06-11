package by.shop.task4.controller;

import by.shop.task4.command.Command;
import by.shop.task4.command.CommandType;
import by.shop.task4.controller.router.Router;
import by.shop.task4.controller.router.RouterType;
import by.shop.task4.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    @Override
    public void init() {
        LOGGER.info("Controller servlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String commandStr = request.getParameter("command");

        try {
            Command command = CommandType.defineCommand(commandStr);
            Router router = command.execute(request);
            if (router.getType() == RouterType.FORWARD) {
                request.getRequestDispatcher(router.getPage()).forward(request, response);
            } else {
                response.sendRedirect(router.getPage());
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Unknown command: {}", commandStr);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown command");
        } catch (CommandException e) {
            LOGGER.error("Command execution failed", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("Controller servlet destroyed");
    }
}
