package co.com.quind.model.customer;
import co.com.quind.model.product.Product;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    private List<Product> products;
}
