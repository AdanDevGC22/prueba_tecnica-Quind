package co.com.quind.api.mapper;

import co.com.quind.api.dto.request.ProductRequestDto;
import co.com.quind.api.dto.request.UpdateProductRequestDto;
import co.com.quind.api.dto.response.ProductResponseDto;
import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.product.AccountStatus;
import co.com.quind.model.product.AccountType;
import co.com.quind.model.product.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public static ProductResponseDto toProductResponseDto(Product product){

        return ProductResponseDto.builder()
                .accountType(AccountType.valueOf(product.getAccountType().name()))
                .accountNumber(product.getAccountNumber())
                .status(AccountStatus.valueOf(product.getStatus().name()))
                .balance(product.getBalance())
                .exemptGMF(product.isExemptGMF())
                .customerName(product.getCustomer().getNames()+" "+(product.getCustomer().getLastname()))
                .build();
    }

    public static  Product toDomain(ProductRequestDto productRequestDto){
        Customer customer = new Customer();
        customer.setId(productRequestDto.getCustomerId());
       // IllegalArgumentException
        AccountType accountType;
        try{
            accountType = co.com.quind.model.product.AccountType.valueOf(productRequestDto.getAccountType());
        }catch (IllegalArgumentException e){
            throw new QuindException(ErrorCode.B409007);
        }

        return Product.builder()
                .accountType(accountType)
                .balance(productRequestDto.getBalance())
                .exemptGMF(productRequestDto.isExemptGMF())
                .customer(customer)
                .build();
    }
    public static Product updateToDamin(UpdateProductRequestDto updateProductRequestDto){
        AccountStatus accountStatus;
        try{
            accountStatus = co.com.quind.model.product.AccountStatus.valueOf(updateProductRequestDto.getStatus());
        }catch (IllegalArgumentException e){
            throw new QuindException(ErrorCode.B409008);
        }
        return Product.builder()
                .accountNumber(updateProductRequestDto.getAccountNumber())
                .status(accountStatus)
                .exemptGMF(updateProductRequestDto.isExemptGMF())
                .build();
    }
}
