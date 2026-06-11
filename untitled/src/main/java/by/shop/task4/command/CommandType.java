package by.shop.task4.command;

import by.shop.task4.command.impl.*;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    GET_ALL_ITEMS(new GetAllItemsCommand()),
    ADD_ITEM(new AddItemCommand()),
    DELETE_ITEM(new DeleteItemCommand()),
    UPDATE_ITEM(new UpdateItemCommand()),
    GET_ALL_USERS(new GetAllUsersCommand()),
    DELETE_USER(new DeleteUserCommand()),
    GO_TO_LOGIN(new GoToLoginCommand()),
    GO_TO_REGISTER(new GoToRegisterCommand()),
    GO_TO_ADD_ITEM(new GoToAddItemCommand()),
    GO_TO_EDIT_ITEM(new GoToEditItemCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command defineCommand(String commandStr) {
        if (commandStr == null || commandStr.isBlank()) {
            throw new IllegalArgumentException("Command parameter is required");
        }
        return CommandType.valueOf(commandStr.toUpperCase()).command;
    }
}
