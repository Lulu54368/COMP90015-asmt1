package server.response;

import server.Operation;
import server.exception.DuplicateException;
import server.exception.EmptyKeyException;
import server.exception.EmptyValueException;
import server.exception.WordNotFoundException;

public class ResponseHandler {
    public static ErrorResponse handleFailure(Exception e, Operation operation){
        switch(operation){
            case GET -> {
                //if(e instanceof WordNotFoundException)
                return new ErrorResponse(ResponseCode.NOTFOUND, e.getMessage());
            }
            case CREATE -> {
                if(e instanceof EmptyKeyException || e instanceof EmptyValueException)
                    return new ErrorResponse(ResponseCode.BAD_REQUEST, e.getMessage());
                else if(e instanceof DuplicateException){
                    return new ErrorResponse(ResponseCode.DUPLICATE_RECORD, e.getMessage());
                }
            }
            case UPDATE -> {
                if(e instanceof WordNotFoundException){
                    return new ErrorResponse(ResponseCode.NOTFOUND, e.getMessage());
                }
                else if(e instanceof EmptyValueException || e instanceof  EmptyKeyException){
                    return new ErrorResponse(ResponseCode.BAD_REQUEST, e.getMessage());
                }

            }
            case DELETE -> {
                if(e instanceof  WordNotFoundException){
                    return new ErrorResponse(ResponseCode.NOTFOUND, e.getMessage());
                }
                else if(e instanceof EmptyValueException || e instanceof  EmptyKeyException){
                    return new ErrorResponse(ResponseCode.BAD_REQUEST, e.getMessage());
                }
            }
        }
        System.err.println(e.getMessage());
        return new ErrorResponse(ResponseCode.INTERNAL_ERROR, e.getMessage());
    }
    public  static SuccessResponse handleSuccess(String definition, Operation operation){
        switch (operation){
            case GET -> {
                return new SuccessResponse(ResponseCode.OK, definition);
            }
            case CREATE -> {
                return new SuccessResponse(ResponseCode.CREATED, null);
            }
            case UPDATE, DELETE -> {
                return new SuccessResponse(ResponseCode.OK, null);
            }
        }
        return new SuccessResponse(ResponseCode.OK, null);

    }
}
