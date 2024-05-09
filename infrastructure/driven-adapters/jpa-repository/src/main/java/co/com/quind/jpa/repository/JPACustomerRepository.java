package co.com.quind.jpa.repository;

import co.com.quind.jpa.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPACustomerRepository extends JpaRepository<CustomerEntity, Long> {
    boolean existsByIdentificationNumber(String identificationNumber);
}
