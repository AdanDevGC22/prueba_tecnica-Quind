package co.com.quind.api.dto.request;

import co.com.quind.model.product.AccountStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateProductRequestDto {
    private String accountNumber;
    private String status;
    private boolean exemptGMF;
}
