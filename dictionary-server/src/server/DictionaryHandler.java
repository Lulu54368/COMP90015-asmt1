package server;

import server.exception.DuplicateException;
import server.exception.EmptyKeyException;
import server.exception.EmptyValueException;
import server.exception.WordNotFoundException;

import java.util.HashMap;

public class DictionaryHandler {
    private  static HashMap<String, String> hs = new HashMap<>();
    public static synchronized String doOperation(RequestModel requestModel) throws EmptyKeyException,
            EmptyValueException, WordNotFoundException, DuplicateException {
        switch (requestModel.operation){
            case GET -> {
                if(!hs.containsKey(requestModel.key)){
                    throw new WordNotFoundException();
                }
                return hs.get(requestModel.value);
            }
            case CREATE -> {
                if(requestModel.key == ""){
                    throw new EmptyKeyException();
                }
                if(requestModel.value == ""){
                    throw new EmptyValueException();
                }
                if(!hs.containsKey(requestModel.value)){
                    throw new DuplicateException();
                }
                hs.put(requestModel.key, requestModel.value);
            }
            case DELETE -> {
                if(!hs.containsKey(requestModel.key)){
                    throw new WordNotFoundException();
                }
                hs.remove(requestModel.key);
            }
            case UPDATE -> {
                if(!hs.containsKey(requestModel.key)){
                    throw new WordNotFoundException();
                }
                hs.put(hs.get(requestModel.key), requestModel.value);
            }
        }
        return null;
    }
}
