package server.response;

import server.Operation;

import java.util.HashMap;
import java.util.Map;

public enum ResponseCode {
    OK(200),
    CREATED(201),
    NOTFOUND(404),
    BAD_REQUEST(400),
    DUPLICATE_RECORD(409),
    INTERNAL_ERROR(500);

    private final Integer code;
    ResponseCode(Integer code){
        this.code = code;
    }
    public Integer getCode(){
        return code;
    }
    private static final Map<Integer, ResponseCode> BY_VALUE = new HashMap<>();

    static {
        for (ResponseCode responseCode: values()) {
            BY_VALUE.put(responseCode.getCode(), responseCode);
        }
    }

    public static ResponseCode fromValue(Integer code) {
        return BY_VALUE.get(code);
    }

}
