package by.shop.task4.exception;

public class CommandException extends Exception{
    public CommandException(){}

    public CommandException(String message){
        super(message);
    }

    public CommandException(Throwable couse){
        super(couse);
    }

    public CommandException(String message, Throwable couse){
        super(message, couse);
    }
}
