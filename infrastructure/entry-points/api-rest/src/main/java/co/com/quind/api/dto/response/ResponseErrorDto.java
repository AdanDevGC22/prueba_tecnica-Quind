package co.com.quind.api.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseErrorDto {
    private String code;
    private String message;
}
