package by.shop.task4.controller.router;


public class Router {
    private String page;
    private RouterType type;

    public Router() {
    }
    public Router(String page, RouterType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public void setForward() {
        this.type = RouterType.FORWARD;
    }

    public void setRedirect() {
        this.type = RouterType.REDIRECT;
    }

    public static Router forwardTo(String page) {
        Router router = new Router();
        router.setForward();
        router.page = page;
        return router;
    }

    public static Router redirectTo(String page) {
        Router router = new Router();
        router.setRedirect();
        router.page = page;
        return router;
    }
}