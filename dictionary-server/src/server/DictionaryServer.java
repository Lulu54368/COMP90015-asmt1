package server;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class DictionaryServer {
    private static int threadNumber = 10;

    public static void setThreadNumber(int threadNumber) {
        DictionaryServer.threadNumber = threadNumber;
    }

    public static void main(String[] args) throws IOException, ParseException {
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
        // initialise the GUI and wait until we are allowed to start the server
        ServerGUI gui = new ServerGUI();
        while (gui.isRunning() && !gui.shouldServerStart()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.err.println("ServerError: main is interrupted, exiting");
                System.exit(1);
            }
        }

        // if GUI is no longer running, we shut down the server
        if (!gui.isRunning()) {
            System.out.println("Server shutdown: exiting");
            System.exit(0);
        }
        ExecutorService executor = Executors.newFixedThreadPool(threadNumber);

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
