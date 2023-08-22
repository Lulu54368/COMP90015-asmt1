package server;

import org.json.simple.parser.ParseException;
import server.exception.*;
import server.response.*;

import java.io.*;
import java.net.Socket;

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
            String requestBody = input.readLine();
            requestModel = ModelParser.parse(requestBody);
            System.out.println("parsed successfully and the request model is " + requestModel.toString());
            RequestValidator.validateRequestBody(requestModel);
            String result = DictionaryHandler.doOperation(requestModel);
            System.out.println("result: "+ result);
            response = ResponseHandler.handleSuccess(result, requestModel.operation);
            System.out.println("response: "+ response);
            System.out.println(response);
            output.write(response.toString()+"\n");
            output.flush();

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
        } catch (IllegalRequestBodyException|ParseException e) {
            response = new ErrorResponse(ResponseCode.BAD_REQUEST, e.getMessage());
            System.err.println("The request body is illegal");
        } catch (EmptyKeyException |DuplicateException|EmptyValueException|WordNotFoundException e) {
            System.err.println("Exception thrown: "+e.getMessage());
            response = ResponseHandler.handleFailure(e, requestModel.operation);

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
