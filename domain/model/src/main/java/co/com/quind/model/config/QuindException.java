package co.com.quind.model.config;

import lombok.Getter;

@Getter
public class QuindException extends RuntimeException {

    private final ErrorCode error;


    public QuindException(ErrorCode errorCode) {
        super(errorCode.getLog());
        this.error = errorCode;
    }
}
