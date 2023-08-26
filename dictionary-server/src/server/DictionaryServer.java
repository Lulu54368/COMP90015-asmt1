package server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.settings.Server;
import org.codehaus.plexus.logging.LoggerManager;
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
    private static Logger logger = LogManager.getLogger(DictionaryServer.class);
    public static void setThreadNumber(int threadNumber) {
        DictionaryServer.threadNumber = threadNumber;
    }

    public static void main(String[] args) throws IOException, ParseException {
        if (args.length < 2) {
            logger.error("Please enter proper input!");
            System.exit(1);
        }
        portNumber = Integer.parseInt(args[0]);
        final String filePath = args[1];
        //load file
        try{
            DictionaryHandler.initDictionaryFile(filePath);
            logger.info("Successfully load the file to the dictionary");
        }
        catch (IOException|ParseException e){
            logger.error("Failed to load the file!");
            System.exit(-1);
        }
        //creating a new thread initializing gui
        new Thread(()->new ServerGUI()).start();

    }
    public static void startServer()  {

        executor = Executors.newFixedThreadPool(threadNumber);
        try{
            s  = new ServerSocket(portNumber);
        }catch (IOException e){
            logger.error("Error occurred when creating socket");
        }

        while(true){
            try{
                Socket clientSocket = s.accept();
                logger.info("Accepted connection from " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executor.execute(clientHandler);

            }catch (IOException e){
                if(!Thread.interrupted()){
                    logger.error("IO exception thrown while accepting request");
                }
                break;
            }
        }



    }
    public static void stopServer()  {
        executor.shutdown();
        try{
            if(!s.isClosed()){
                s.close();
            }
        }catch (IOException e){
            logger.error("Error occurred while closing the server");
            return;
        }

        logger.info("The server stopped.");
    }


}
