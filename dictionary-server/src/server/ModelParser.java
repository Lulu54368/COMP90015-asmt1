package server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;

public class ModelParser {
    public static RequestModel parse(String requestBody) throws IOException, ParseException {
        RequestModel requestModel = new RequestModel();
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(requestBody);
        requestModel.operation = Operation.fromValue((String) jsonObject.get("operation"));
        //If the operation is illegal
        requestModel.key = (String) jsonObject.get("key");
        requestModel.value = (String) jsonObject.get("value");
        return requestModel;


    }
}
