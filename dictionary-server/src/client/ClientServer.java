package client;

import org.json.simple.JSONObject;
import server.Operation;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer {
    private static BufferedWriter os;
    private static BufferedReader is;
    private static ClientGUI clientGUI;
    public static void main(String[] args) {


        if (args.length < 2) {
            System.err.println("Please enter proper arguments");
            System.exit(1);
        }

        final String serverHostname = args[0];
        final int serverPortNumber = Integer.parseInt(args[1]);

        // try to initialise connection with the server
        Socket client = null;
        try {
            client = new Socket(serverHostname, serverPortNumber);
            is = new BufferedReader(new InputStreamReader(client.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            clientGUI = new ClientGUI();
        } catch (IOException e) {
            System.err.println("ClientError: unable to connect to server at address '" + serverHostname + "' on port "
                    + serverPortNumber + ", exiting");
            System.exit(1);
        }


    }

    public static String parseRequestBody(Operation operation, String word, String def){
        JSONObject obj = new JSONObject();
        obj.put("operation", operation.getValue());
        obj.put("key", word);
        obj.put("value", def);
        return obj.toJSONString();
    }
    public static void submitRequest(Operation operation, String word, String def) {
        try{
            String requestBody = parseRequestBody(operation, word, def);
            System.out.println(requestBody);
            os.write(requestBody);
            os.flush();
        }catch (IOException e){
            System.err.println("Error occurred submitting request");
        }
        //Read line need to be modified
        try {
            Thread.sleep(1000);
            String result = is.readLine();
            System.out.println("result is "+result);
            if(result != null)
                clientGUI.setResultTextArea(is.readLine());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
