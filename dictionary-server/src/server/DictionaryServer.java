package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DictionaryServer {
    private static final int threadNumber = 10;

    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        if (args.length < 2) {
            System.err.println("Please enter proper input!");
            System.exit(1);
        }

        final int portNumber = Integer.parseInt(args[0]);
        final String filePath = args[1];
        //load file

        //initialize GUI
        try{
            ServerSocket s = new ServerSocket(portNumber);
            while (true) {
                Socket clientSocket = s.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.execute(clientHandler);
            }
        }catch (IOException e){
            System.err.println("IO exception thrown while setting up server");
            System.exit(1);
        }finally{
            executor.shutdown();
        }



        //set up socket

    }


}
