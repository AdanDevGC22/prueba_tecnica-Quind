package co.com.quind.jpa.repository;

import co.com.quind.jpa.entities.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface JPACustomerRepository extends CrudRepository<CustomerEntity, Long>, QueryByExampleExecutor<CustomerEntity> {
}
