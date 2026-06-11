package by.shop.task4.exception;

public class ServiceException extends Exception{
    public ServiceException(){}

    public ServiceException(String message){
        super(message);
    }

    public ServiceException(Throwable couse){
        super(couse);
    }

    public ServiceException(String message, Throwable couse){
        super(message, couse);
    }
}
