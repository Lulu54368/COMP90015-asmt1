package server;

import java.io.BufferedReader;
import java.io.IOException;

public class ModelParser {
    public static RequestModel parse(BufferedReader br) throws IOException {
        RequestModel requestModel = new RequestModel();
        StringBuilder lines = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            lines.append(line);
        }
        String requestBody= lines.toString();
        String[] parts = requestBody.split(",");
        if (parts.length == 3) {
            requestModel.operation = Operation.fromValue(parts[1]);
            //If the operation is illegal
            requestModel.key = parts[1];
            requestModel.value = parts[2];
            return requestModel;
        }else{
            //illegal input from client
            throw new IllegalArgumentException();
        }

    }
}
