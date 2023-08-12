package server;

import java.util.HashMap;
import java.util.Map;

public enum Operation {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    GET("get");
    private final String value;

    Operation(String value) {
        this.value = value;
    }


    // Static initializer block
    private static final Map<String, Operation> BY_VALUE = new HashMap<>();

    static {
        for (Operation operation: values()) {
            BY_VALUE.put(operation.value, operation);
        }
    }

    public static Operation fromValue(String value) {
        return BY_VALUE.get(value);
    }
}
