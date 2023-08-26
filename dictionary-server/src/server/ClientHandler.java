package server;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.json.simple.parser.ParseException;
import server.exception.*;
import server.response.*;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    Socket clientSocket;
    private static Logger logger = LogManager.getLogger(ClientHandler.class);


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
            String requestBody;
            while ((requestBody = input.readLine()) != null) {
                logger.debug("request body " + requestBody);
                requestModel = ModelParser.parse(requestBody);
                logger.debug("parsed successfully and the request model is " + requestModel.toString());
                RequestValidator.validateRequestBody(requestModel);
                String result = DictionaryHandler.doOperation(requestModel);
                logger.debug("result: " + result);
                response = ResponseHandler.handleSuccess(result, requestModel.operation);
                logger.debug("response: " + response);
                logger.debug(response.getJSONObject().toString());
                output.write(response.getJSONObject().toString()+ "\n");
                output.flush();
            }

        } catch (IOException e) {
            logger.debug("ClientHandlerError: unable to read client stream, closing socket and exiting");
            if (!this.clientSocket.isClosed()) {
                try {
                    this.clientSocket.close();
                } catch (IOException err) {
                    logger.error("ClientHandlerError: unable to close client socket, exiting");
                }
            }
            return;
        } catch (IllegalRequestBodyException|ParseException e) {
            response = new ErrorResponse(ResponseCode.BAD_REQUEST, e.getMessage());
            logger.error("The request body is illegal");
            try {
                output.write(response.getJSONObject().toString()+ "\n");
                output.flush();
            } catch (IOException ex) {
                logger.error("unable to write to the client");
            }

        } catch (EmptyKeyException |DuplicateException|EmptyValueException|WordNotFoundException e) {
            logger.error("Exception thrown: "+e.getMessage());
            response = ResponseHandler.handleFailure(e, requestModel.operation);
            try {
                output.write(response.getJSONObject().toString()+ "\n");
                output.flush();
            } catch (IOException ex) {
                logger.error("unable to write to the client");

            }


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
            logger.error("ClientHandlerError: unable to close client socket, exiting");
        }


    }
}
