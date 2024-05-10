package co.com.quind.jpa.adapter;

import co.com.quind.jpa.entities.ProductEntity;
import co.com.quind.jpa.mapper.CustomerMapper;
import co.com.quind.jpa.mapper.ProductMapper;
import co.com.quind.jpa.repository.JPAProductRepository;
import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JPAProductRepositoryAdapter implements ProductRepository {

    private final JPAProductRepository jpaProductRepository;

    @Override
    public Product save(Product product) {
        return ProductMapper.toProduct(jpaProductRepository.save(ProductMapper.toProductEntity(product)));
    }

    @Override
    public Product getProductByAccountNumber(String accountNumber) {
        Optional<ProductEntity> productEntity = jpaProductRepository.findByAccountNumber(accountNumber);

        if (productEntity.isEmpty()) {
            throw new QuindException(ErrorCode.B404001);
        }
        return ProductMapper.toProduct(productEntity.get());
    }

    @Override
    public void delete(Product product) {
        try {
            jpaProductRepository.delete(ProductMapper.toProductEntity(product));
        } catch (Exception e) {
            throw new QuindException(ErrorCode.B409004);
        }
    }

    @Override
    public List<Product> getAllByCustomer(Customer customer) {
        return ProductMapper.toListProducts(jpaProductRepository.findByCustomer(CustomerMapper.toCustomerEntity(customer)));
    }
}
