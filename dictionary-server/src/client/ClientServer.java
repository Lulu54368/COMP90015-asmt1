package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientServer {
    public static void main(String[] args) {
        DataOutputStream os;
        DataInputStream is;

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
            is = new DataInputStream(client.getInputStream() );
            os = new DataOutputStream( client.getOutputStream() );

            System.out.println("requesting...");
        } catch (UnknownHostException e) {
            System.err.println("ClientError: un recogniseable server address: '" + serverHostname + "' (port "
                    + serverPortNumber
                    + "), exiting");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("ClientError: unable to connect to server at address '" + serverHostname + "' on port "
                    + serverPortNumber + ", exiting");
            System.exit(1);
        }

    }


}
