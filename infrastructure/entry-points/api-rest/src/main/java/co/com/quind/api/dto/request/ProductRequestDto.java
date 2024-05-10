package co.com.quind.api.dto.request;

import co.com.quind.model.product.AccountType;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {

    private String accountType;
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private BigDecimal balance;
    private boolean exemptGMF;
    private Long customerId;
}
