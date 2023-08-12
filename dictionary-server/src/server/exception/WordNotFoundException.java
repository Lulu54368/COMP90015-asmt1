package server.exception;

public class WordNotFoundException extends Exception{
    public WordNotFoundException(){
        super("The word can not be found in the dictionary.");
    }
}
