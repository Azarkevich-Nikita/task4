package by.shop.task4.exception;

public class DaoException extends Exception{
    public DaoException(){}

    public DaoException(String message){
        super(message);
    }

    public DaoException(Throwable couse){
        super(couse);
    }

    public DaoException(String message, Throwable couse){
        super(message, couse);
    }
}
