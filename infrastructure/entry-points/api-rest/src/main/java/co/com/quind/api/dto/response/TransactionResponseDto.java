package co.com.quind.api.dto.response;

import co.com.quind.model.transaction.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto {
    private TransactionType type;
    private BigDecimal amount;
    private String sourceAccountNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String destinationAccountNumber;
    private LocalDateTime transactionDate;

}
