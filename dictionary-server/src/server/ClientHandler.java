package server;

import server.exception.*;
import server.response.*;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientHandler implements Runnable{
    Socket clientSocket;


    public ClientHandler(Socket clientSocketSocket) {
        this.clientSocket = clientSocketSocket;
    }

    @Override
    public void run() {
        BufferedReader input = null;
        BufferedWriter output = null;
        Response response;
        RequestModel requestModel = new RequestModel();
        // try to set up client socket's I/O streams
        try {
            input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
            requestModel = ModelParser.parse(input);
            RequestValidator.validateRequestBody(requestModel);
            String result = DictionaryHandler.doOperation(requestModel);
            response = ResponseHandler.handleSuccess(result, requestModel.operation);

        } catch (IOException e) {
            System.err.println("ClientHandlerError: unable to read client stream, closing socket and exiting");
            if (!this.clientSocket.isClosed()) {
                try {
                    this.clientSocket.close();
                } catch (IOException err) {
                    System.err.println("ClientHandlerError: unable to close client socket, exiting");
                }
            }
            return;
        } catch (IllegalRequestBodyException e) {
            response = new ErrorResponse(ResponseCode.BAD_REQUEST, e.getMessage());
        } catch (EmptyKeyException |DuplicateException|EmptyValueException|WordNotFoundException e) {
            response = ResponseHandler.handleFailure(e, requestModel.operation);
        }
        //Send response to the client
        try {
            output.write(response.toString());
            output.flush();
        } catch (IOException e) {
            System.err.println("Unable to write response to the client");
        }
        try {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (!this.clientSocket.isClosed()) {
                this.clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("ClientHandlerError: unable to close client socket, exiting");
        }


    }
}
