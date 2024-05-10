package co.com.quind.api.dto.response;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {

    private String identificationType;
    private String identificationNumber;
    private String names;
    private String lastname;
    private String email;
    private LocalDate birthdate;
}
