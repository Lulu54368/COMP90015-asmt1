package server;

public class ExceptionHandler {
    public static ErrorResponse handle(Exception e, String customErrorMsg) {
        String errorMsg = "";

        errorMsg = e.getMessage();
    }
}


