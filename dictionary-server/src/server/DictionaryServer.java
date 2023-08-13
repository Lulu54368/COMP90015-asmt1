package server;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class DictionaryServer {
    private static final int threadNumber = 10;

    public static void main(String[] args) throws IOException, ParseException {
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);
        if (args.length < 2) {
            System.err.println("Please enter proper input!");
            System.exit(1);
        }

        final int portNumber = Integer.parseInt(args[0]);
        final String filePath = args[1];
        //load file
        try{
            DictionaryHandler.initDictionaryFile(filePath);
            System.out.println("Successfully load the file to the dictionary");
        }
        catch (IOException|ParseException e){
            System.err.println("Failed to load the file!");
            System.exit(-1);
        }

        //initialize GUI
        try{
            //check whether the port is valid
            ServerSocket s = new ServerSocket(portNumber);
            System.out.println("listening...");
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




    }


}
