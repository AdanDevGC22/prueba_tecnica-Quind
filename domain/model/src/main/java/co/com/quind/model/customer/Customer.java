package co.com.quind.model.customer;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
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
