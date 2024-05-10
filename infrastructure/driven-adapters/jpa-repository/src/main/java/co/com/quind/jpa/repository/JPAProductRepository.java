package co.com.quind.jpa.repository;

import co.com.quind.jpa.entities.CustomerEntity;
import co.com.quind.jpa.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JPAProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByAccountNumber(String accountNumber);
    List<ProductEntity> findByCustomer(CustomerEntity customer);
}
