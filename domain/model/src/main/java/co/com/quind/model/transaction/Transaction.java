package co.com.quind.model.transaction;
import co.com.quind.model.product.Product;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Transaction {
    private Long id;
    private String type;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private Product account;
}
