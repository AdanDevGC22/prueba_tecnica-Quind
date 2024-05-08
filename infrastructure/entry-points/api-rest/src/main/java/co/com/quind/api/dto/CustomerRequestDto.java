package co.com.quind.api.dto;

import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDto {
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String lastname;
    private String email;
    private LocalDate birthdate;
}
