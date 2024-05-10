package co.com.quind.jpa.mapper;

import co.com.quind.jpa.entities.AccountStatus;
import co.com.quind.jpa.entities.AccountType;
import co.com.quind.jpa.entities.ProductEntity;
import co.com.quind.model.product.Product;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ProductMapper {
    public static ProductEntity toProductEntity(Product product){

        return ProductEntity.builder()
                .id(product.getId())
                .accountType(AccountType.valueOf(product.getAccountType().name()))
                .accountNumber(product.getAccountNumber())
                .status(AccountStatus.valueOf(product.getStatus().name()))
                .balance(product.getBalance())
                .exemptGMF(product.isExemptGMF())
                .creationDate(product.getCreationDate())
                .modificationDate(product.getModificationDate())
                .customer(CustomerMapper.toCustomerEntity(product.getCustomer()))
                .build();
    }

    public static  Product toProduct(ProductEntity productEntity){

        return Product.builder()
                .id(productEntity.getId())
                .accountType(co.com.quind.model.product.AccountType.valueOf(productEntity.getAccountType().name()))
                .accountNumber(productEntity.getAccountNumber())
                .status(co.com.quind.model.product.AccountStatus.valueOf(productEntity.getStatus().name()))
                .balance(productEntity.getBalance())
                .exemptGMF(productEntity.isExemptGMF())
                .creationDate(productEntity.getCreationDate())
                .modificationDate(productEntity.getModificationDate())
                .customer(CustomerMapper.toCustomer(productEntity.getCustomer()))
                .build();
    }

    public static List<Product> toListProducts(List<ProductEntity> productEntities){
        return  productEntities.stream()
                .map(ProductMapper::toProduct).toList();
    }
}
