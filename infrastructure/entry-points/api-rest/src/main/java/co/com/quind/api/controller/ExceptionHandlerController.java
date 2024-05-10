package co.com.quind.api.controller;

import co.com.quind.api.dto.response.ResponseErrorDto;
import co.com.quind.api.mapper.ErrorResponseMapper;
import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.ErrorDictionary;
import co.com.quind.model.config.QuindException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDto> handleConstraintViolationException(MethodArgumentNotValidException e) {
        return genericHandleException(ErrorCode.B400000);
    }

    @ExceptionHandler(QuindException.class)
    public ResponseEntity<ResponseErrorDto> handleQuindException(QuindException e) {
        return genericHandleException(e.getError());
    }

    private ResponseEntity<ResponseErrorDto> genericHandleException(ErrorCode errorCode) {
        ErrorDictionary errorDictionary = ErrorDictionary.builder()
                .id(errorCode.getCode())
                .message(errorCode.getLog())
                .httpStatus(errorCode.getStatus())
                .build();

        ResponseErrorDto responseErrorDto = ErrorResponseMapper.toResponseErrorDto(errorDictionary);

        return ResponseEntity
                .status(Objects.requireNonNull(HttpStatus.resolve(errorDictionary.getHttpStatus())))
                .body(responseErrorDto);
    }
}
