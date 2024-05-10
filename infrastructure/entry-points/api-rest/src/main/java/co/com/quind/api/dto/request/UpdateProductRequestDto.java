package co.com.quind.api.dto.request;

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
