package co.com.quind.model.customer.config;

import lombok.Getter;

@Getter
public enum ErrorCode {
    B400000("B400-000", "Bad Request-fields bad format", 400),
    B409000("B409-000", "The client is a minor", 409),
    B409001("B409-001", "The customer already exists", 409),
    B409002("B409-002", "The customer has linked products.", 409),
    B404000("B404-000", "Customer does not exist", 404),
    B409003("B409-003", "Error when deleting customer in database", 409);

    private final String code;
    private final String log;
    private final int status;

    ErrorCode(String code, String log, int status) {
        this.code = code;
        this.log = log;
        this.status = status;
    }
}
