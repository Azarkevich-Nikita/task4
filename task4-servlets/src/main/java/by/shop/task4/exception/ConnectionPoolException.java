package by.shop.task4.exception;

public class ConnectionPoolException extends Exception{
    public ConnectionPoolException(){}

    public ConnectionPoolException(String message){
        super(message);
    }

    public ConnectionPoolException(Throwable couse){
        super(couse);
    }

    public ConnectionPoolException(String message, Throwable couse){
        super(message, couse);
    }
}
