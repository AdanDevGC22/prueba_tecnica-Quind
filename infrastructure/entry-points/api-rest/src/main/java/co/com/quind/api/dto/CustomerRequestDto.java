package co.com.quind.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequestDto {
    @NotNull
    private String identificationType;
    @NotNull
    private String identificationNumber;
    @NotNull
    @Size(min = 3, max = 100)
    private String names;
    @NotNull
    @Size(min = 3, max = 100)
    private String lastname;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
    private String email;
    @NotNull
    private LocalDate birthdate;
}
