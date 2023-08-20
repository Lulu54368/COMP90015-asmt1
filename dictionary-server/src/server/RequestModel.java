package server;

public class RequestModel {

    Operation operation;
    String key;

    @Override
    public String toString() {
        return "RequestModel{" +
                "operation=" + operation +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    String value;
}
