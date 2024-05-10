package co.com.quind.jpa.adapter;

import co.com.quind.jpa.entities.CustomerEntity;
import co.com.quind.jpa.mapper.CustomerMapper;
import co.com.quind.jpa.repository.JPACustomerRepository;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JPACustomerRepositoryAdapter implements CustomerRepository {


    private final JPACustomerRepository jpaCustomerRepository;

    @Override
    public List<Customer> getAll() {
        return jpaCustomerRepository.findAll()
                .stream()
                .map(CustomerMapper::toCustomer)
                .toList();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity customerEntity = jpaCustomerRepository.save(CustomerMapper.toCustomerEntity(customer));
        return CustomerMapper.toCustomer(customerEntity);
    }

    @Override
    public boolean existsCustomerByIdentificationNumber(String identificationNumber) {
        return jpaCustomerRepository.existsByIdentificationNumber(identificationNumber);
    }

    @Override
    public Customer getCustomerById(Long id) {
        Optional<CustomerEntity> customerEntity = jpaCustomerRepository.findById(id);

        if (customerEntity.isEmpty()) {
            throw new QuindException(ErrorCode.B404000);
        }
        return CustomerMapper.toCustomer(customerEntity.get());
    }

    @Override
    public void delete(Long id) {
        try {
            jpaCustomerRepository.deleteById(id);
        } catch (Exception e) {
            throw new QuindException(ErrorCode.B409003);
        }

    }

    @Override
    public boolean existsById(Long id) {
        return jpaCustomerRepository.existsById(id);
    }

    @Override
    public Customer findByIdentificationNumberAndIdentificationType(String identificationNumber, String identificationType) {
        return CustomerMapper.toCustomer(jpaCustomerRepository.findByIdentificationNumberAndIdentificationType(identificationNumber, identificationType));
    }
}
