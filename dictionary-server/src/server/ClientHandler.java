package server;

import server.exception.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket clientSocket;

    public ClientHandler(Socket clientSocketSocket) {
        this.clientSocket = clientSocketSocket;
    }

    @Override
    public void run() {
        BufferedReader input;
        BufferedWriter output;

        // try to set up client socket's I/O streams
        try {
            input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            RequestModel requestModel = ModelParser.parse(input);
            RequestValidator.validateRequestBody(requestModel);
            DictionaryHandler.doOperation(requestModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalRequestBodyException e) {
            throw new RuntimeException(e);
        } catch (EmptyKeyException e) {
            throw new RuntimeException(e);
        } catch (DuplicateException e) {
            throw new RuntimeException(e);
        } catch (EmptyValueException e) {
            throw new RuntimeException(e);
        } catch (WordNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Send a string!


    }
}
