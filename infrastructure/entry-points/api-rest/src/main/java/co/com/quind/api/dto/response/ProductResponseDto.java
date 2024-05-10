package co.com.quind.api.dto.response;

import co.com.quind.model.product.AccountStatus;
import co.com.quind.model.product.AccountType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private AccountType accountType;
    private String accountNumber;
    private AccountStatus status;
    private BigDecimal balance;
    private boolean exemptGMF;
    private String customerName;
}
