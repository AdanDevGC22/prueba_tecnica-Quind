package co.com.quind.model.customer;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Customer {

    private Long id;
    private String identificationType;
    private String identificationNumber;
    private String names;
    private String lastname;
    private String email;
    private LocalDate birthdate;
    private LocalDateTime creationDate;
    private LocalDateTime modificationDate;
}
