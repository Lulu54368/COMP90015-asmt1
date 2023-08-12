package server.exception;

public class EmptyKeyException extends Exception{
    public EmptyKeyException(){
        super("key is empty.");
    }
}
