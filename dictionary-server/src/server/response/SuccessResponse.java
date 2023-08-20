package server.response;

public class SuccessResponse extends Response{
    String definition;

    public SuccessResponse(ResponseCode responseCode, String definition) {
        this.responseCode = responseCode;
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "SuccessResponse{" +
                "definition='" + definition + '\'' +
                ", responseCode=" + responseCode +
                '}';
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
