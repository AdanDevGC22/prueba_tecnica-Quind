package co.com.quind.jpa.adapter;

import co.com.quind.jpa.entities.CustomerEntity;
import co.com.quind.jpa.helper.AdapterOperations;
import co.com.quind.jpa.mapper.CustomerMapper;
import co.com.quind.jpa.repository.JPACustomerRepository;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.gateways.CustomerRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPACustomerRepositoryAdapter extends AdapterOperations<Customer, CustomerEntity, Long, JPACustomerRepository>
    implements CustomerRepository
{

    public JPACustomerRepositoryAdapter(JPACustomerRepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, Customer.class));
    }

    @Override
    public List<Customer> getAll() {
        return findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        CustomerEntity customerEntity = saveData(CustomerMapper.toCustomerEntity(customer));
        return CustomerMapper.toCustomer(customerEntity);
    }
}
