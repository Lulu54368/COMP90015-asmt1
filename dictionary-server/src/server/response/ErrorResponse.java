package server.response;

public class ErrorResponse extends Response{
    String message;

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }

    public ErrorResponse(ResponseCode responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }
}
