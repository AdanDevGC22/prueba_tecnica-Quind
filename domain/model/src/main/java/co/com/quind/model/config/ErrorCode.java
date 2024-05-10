package co.com.quind.model.config;

import lombok.Getter;

@Getter
public enum ErrorCode {
    B400000("B400-000", "Bad Request-fields bad format", 400),
    B409000("B409-000", "The client is a minor", 409),
    B409001("B409-001", "The customer already exists", 409),
    B409002("B409-002", "The customer has linked products.", 409),
    B404000("B404-000", "Customer does not exist", 404),
    B404001("B404-001", "Product does not exist", 404),
    B409003("B409-003", "Error when deleting customer in database", 409),
    B409004("B409-004", "Error when deleting Product in database", 409),
    B409005("B409-005", "Product cannot be deleted, because the balance is not equal to 0", 409),
    B409006("B409-006", "Customer cannot be deleted because has products", 409),
    B409007("B409-007", "Product cannot be created because it must be AHORROS or CORRIENTE", 409),
    B409008("B409-008", "Error, the status should be ACTIVA, INACTIVA, or CANCELADA", 409),
    B409009("B409-009", "The amount must be greater than zero.", 409),
    B409010("B409-010", "Insufficient balance ", 409);

    private final String code;
    private final String log;
    private final int status;

    ErrorCode(String code, String log, int status) {
        this.code = code;
        this.log = log;
        this.status = status;
    }
}
