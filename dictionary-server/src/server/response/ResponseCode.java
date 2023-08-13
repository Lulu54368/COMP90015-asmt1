package server.response;

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
}
