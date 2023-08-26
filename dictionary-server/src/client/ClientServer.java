package client;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.Operation;
import server.exception.IllegalOperationException;
import server.exception.IllegalRequestBodyException;

import java.io.*;
import java.net.Socket;

public class ClientServer {
    private static BufferedWriter os;
    private static BufferedReader is;
    private static ClientGUI clientGUI;
    private static String serverHostname;
    private static Integer serverPortNumber;
    public static void main(String[] args) {


        if (args.length < 2) {
            System.err.println("Please enter proper arguments");
            System.exit(1);
        }

        serverHostname = args[0];
        serverPortNumber = Integer.parseInt(args[1]);
        try {
            buildConnection(serverHostname, serverPortNumber);
            clientGUI = new ClientGUI();
        } catch (IOException e) {
            System.err.println("ClientError: unable to connect to server at address '" + serverHostname + "' on port "
                    + serverPortNumber + ", exiting");
            System.exit(1);
        }


    }
    private static void buildConnection(String serverHostname, Integer serverPortNumber) throws IOException {
        Socket client = new Socket(serverHostname, serverPortNumber);
        is = new BufferedReader(new InputStreamReader(client.getInputStream()));
        os = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
    }

    public static String parseRequestBody(Operation operation, String word, String def) throws IllegalRequestBodyException {
        JSONObject obj = new JSONObject();
        if(word == null ||word == ""){
            throw new IllegalRequestBodyException("The request body is illegal");
        }
        if(operation.equals(Operation.CREATE)||operation.equals(Operation.UPDATE)){
            if(def == null|def == "")
            throw new IllegalRequestBodyException("The request body is illegal");
        }
        obj.put("operation", operation.getValue());
        obj.put("key", word);
        obj.put("value", def);
        return obj.toString();
    }

    public static void submitRequest(Operation operation, String word, String def) {
        String response = null;
        try{
            String requestBody = parseRequestBody(operation, word, def);
            System.out.println(requestBody);
            os.write(requestBody+"\n");
            os.flush();
            String result;
            if((result = is.readLine() )!= null){
                ClientResultHandler clientResultHandler = new ClientResultHandler
                        (operation, (JSONObject)(new JSONParser().parse(result)));
                response = clientResultHandler.handleResponse();
                System.out.println("result is "+response);
                clientGUI.setResultTextArea(response);
            }

        }
        catch (IOException e){
            try{
                buildConnection(serverHostname, serverPortNumber);
                submitRequest(operation, word, def);

            }catch (IOException ioException){
                System.err.println("Error occurred building connection");
                System.exit(1);
            }

        }
        catch (IllegalRequestBodyException e){
            response = "Error occurred submitting request";
            System.err.println("Error occurred submitting request" + e.getMessage());
            clientGUI.setResultTextArea(response);
        }catch (ParseException e){
            response = "Error occurred parsing result";
            System.err.println("Error occurred parsing result " + e.getMessage());
            clientGUI.setResultTextArea(response);
        }


    }


}
