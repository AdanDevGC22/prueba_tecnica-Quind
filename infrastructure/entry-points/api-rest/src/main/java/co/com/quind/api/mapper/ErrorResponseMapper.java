package co.com.quind.api.mapper;

import co.com.quind.api.dto.ResponseErrorDto;
import co.com.quind.model.customer.config.ErrorDictionary;

public class ErrorResponseMapper {
    public static ResponseErrorDto toResponseErrorDto(ErrorDictionary errorDictionary) {
        return ResponseErrorDto.builder()
                .code(errorDictionary.getId())
                .message(errorDictionary.getMessage())
                .build();
    }
}
