package server.exception;

public class EmptyValueException extends Exception{
    public EmptyValueException(){
        super("The definition is empty.");
    }
}
