package server.exception;

public class IllegalRequestBodyException extends Exception{
    public IllegalRequestBodyException(String message){
        super(message);
    }
}
