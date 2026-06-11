package by.shop.task4.command.impl.param;

public final class RequestParameter {

    private RequestParameter() {
    }

    public static final String MAIN_PAGE = "/index.jsp";
    public static final String LOGIN_PAGE = "/WEB-INF/pages/login.jsp";
    public static final String REGISTRATION_PAGE = "/WEB-INF/pages/register.jsp";
    public static final String USERS_PAGE = "/WEB-INF/pages/users.jsp";
    public static final String ITEMS_PAGE = "/WEB-INF/pages/items.jsp";
    public static final String ADD_ITEM_PAGE = "/WEB-INF/pages/add_item.jsp";
    public static final String EDIT_ITEM_PAGE = "/WEB-INF/pages/edit_item.jsp";

    public static final String PARAM_USER = "user";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_USER_LIST = "users";

    public static final String PARAM_ITEM = "item";
    public static final String PARAM_ITEM_ID = "id";
    public static final String PARAM_ITEM_TITLE = "title";
    public static final String PARAM_ITEM_PRICE = "price";
    public static final String PARAM_ITEM_DESCRIPTION = "description";
    public static final String PARAM_ITEM_MANUFACTURE_TEAM = "manufactureTeam";
    public static final String PARAM_ITEM_LIST = "items";

    public static final String PARAM_ERROR = "error";
    public static final String PARAM_MESSAGE = "message";
}
