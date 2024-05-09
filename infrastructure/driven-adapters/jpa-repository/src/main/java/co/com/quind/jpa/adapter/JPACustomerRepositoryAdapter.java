package co.com.quind.jpa.adapter;

import co.com.quind.jpa.entities.CustomerEntity;
import co.com.quind.jpa.helper.AdapterOperations;
import co.com.quind.jpa.mapper.CustomerMapper;
import co.com.quind.jpa.repository.JPACustomerRepository;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JPACustomerRepositoryAdapter implements CustomerRepository{

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
}
