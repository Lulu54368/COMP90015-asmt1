package server.exception;

public class DuplicateException extends Exception{
    public DuplicateException(){
        super("The world already exists in the dictionary.");
    }
}
