package client;

import org.json.simple.JSONObject;
import server.Operation;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer {
    public static void main(String[] args) {
        BufferedWriter os;
        BufferedReader is;

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
            String requestBody = parseRequestBody(Operation.GET, "java", "This is java1");
            os.write(requestBody);
            os.flush();
            System.out.println("send the request: "+requestBody
                    + " to server host name: " + serverHostname+
                    " port number: "+ serverPortNumber);
            System.out.println(is.readLine());
        } catch (UnknownHostException e) {
            System.err.println("ClientError: unrecognizeable server address: '" + serverHostname + "' (port "
                    + serverPortNumber
                    + "), exiting");
            System.exit(1);
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


}
