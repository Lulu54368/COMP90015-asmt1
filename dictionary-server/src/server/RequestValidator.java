package server;

import server.exception.IllegalOperationException;
import server.exception.IllegalRequestBodyException;

public class RequestValidator {
    public static void validateRequestBody(RequestModel requestModel) throws IllegalRequestBodyException {
        if(requestModel == null){
            throw new IllegalRequestBodyException("The request body can not be null");
        }
        if(requestModel.operation == null){
            throw new IllegalRequestBodyException("The operation can not be null");
        }
        if(requestModel.operation == Operation.GET && (requestModel.key == ""||requestModel.key == null)){
            throw new IllegalRequestBodyException("The word can not be empty for get request");
        }
        if(requestModel.operation == Operation.CREATE &&
                (requestModel.key == ""||requestModel.key == null
                        ||requestModel.value == ""||requestModel.value == null)){
            throw new IllegalRequestBodyException("The word or definition is not valid for post request");
        }
        if(requestModel.operation == Operation.DELETE && (requestModel.key == ""||requestModel.key == null)){
            throw new IllegalRequestBodyException("The word can not be empty for delete request");
        }
        if(requestModel.operation == Operation.UPDATE &&
                (requestModel.key == ""||requestModel.key == null
                ||requestModel.value == ""||requestModel.value == null)){
            throw new IllegalRequestBodyException("The word or definition is not valid for update request");
        }


    }
}
