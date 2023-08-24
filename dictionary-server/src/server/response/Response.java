package server.response;

import org.json.simple.JSONObject;

public class Response {
    ResponseCode responseCode;

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }
    public String getResult(){
        return null;
    }
    public JSONObject getJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", responseCode.getCode());
        jsonObject.put("result", getResult());
        return jsonObject;
    }
}
