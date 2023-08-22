package server;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class DictionaryServer {
    private static int threadNumber = 10;
    private static int portNumber;
    private static ExecutorService executor;
    private static ServerSocket s;

    public static void setThreadNumber(int threadNumber) {
        DictionaryServer.threadNumber = threadNumber;
    }

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length < 2) {
            System.err.println("Please enter proper input!");
            System.exit(1);
        }

        portNumber = Integer.parseInt(args[0]);
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
        //creating a new thread initializing gui
        new Thread(()->new ServerGUI()).start();

    }
    public static void startServer() {

        executor = Executors.newFixedThreadPool(threadNumber);
        try{
            s = new ServerSocket(portNumber);

            while (true) {
                Socket clientSocket = s.accept();
                System.out.println("Accepted connection from " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.execute(clientHandler);
            }
        }catch (IOException e){
            if(!Thread.interrupted()){
                System.err.println("IO exception thrown while accepting request");
            }
        } finally {
            executor.shutdown();
        }


    }
    public static void stopServer()  {
        executor.shutdown();
        try{
            if(!s.isClosed()){
                s.close();
            }
        }catch (IOException e){
            System.err.println("Error occurred while closing the server");
            return;
        }

        System.out.println("The server stopped.");
    }


}
