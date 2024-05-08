package co.com.quind.usecase.customer;

import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CustomerUseCase {

    private final CustomerRepository customerRepository;
    public List<Customer> getAll(){
        return customerRepository.getAll();
    }
}
