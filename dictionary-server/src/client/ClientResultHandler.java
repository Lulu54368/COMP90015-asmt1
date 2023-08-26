package client;

import org.json.simple.JSONObject;
import server.Operation;
import server.response.ResponseCode;

import static server.response.ResponseCode.OK;

public class ClientResultHandler {
    private Operation operation;
    private JSONObject response;
    private String result;
    private ResponseCode responseCode;

    public ClientResultHandler(Operation operation, JSONObject response) {
        this.operation = operation;
        this.response = response;
        result = (String) response.get("result");
        responseCode = ResponseCode.fromValue(Integer.parseInt(String.valueOf((Long) response.get("code"))));
    }
    public String handleResponse(){
        switch (operation){
            case GET -> {
                switch (responseCode){
                    case OK -> {
                        return "Success: "+ result;
                    }
                    case NOTFOUND -> {
                        return "error: "+ result;
                    }
                    case BAD_REQUEST -> {
                        return "Error: "+ result;
                    }
                }

            }
            case CREATE -> {
                switch (responseCode){
                    case CREATED -> {
                        return "Success: created successfully!";
                    }
                    case DUPLICATE_RECORD -> {
                        return "Error: Already created!";
                    }
                    case BAD_REQUEST -> {
                        return "Error: Invalid request!";
                    }
                    case INTERNAL_ERROR -> {
                        return "Error: can not be created!";
                    }
                }
            }
            case UPDATE -> {
                switch (responseCode){
                    case OK -> {
                        return "Success: Update successfully!";
                    }
                    case BAD_REQUEST -> {
                        return "Error: Invalid request";
                    }
                    case NOTFOUND -> {
                        return "Error: the key can not be found in the dictionary!";
                    }
                }
            }
            case DELETE -> {
                switch (responseCode){
                    case OK -> {
                        return "Success: Delete successfully";
                    }
                    case NOTFOUND -> {
                        return "Error: the key can not be found in the dictionary!";
                    }
                    case BAD_REQUEST -> {
                        return "Error: Invalid request";
                    }
                }
            }

        }
        return null;
    }

}
