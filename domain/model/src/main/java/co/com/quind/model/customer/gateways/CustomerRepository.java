package co.com.quind.model.customer.gateways;

import co.com.quind.model.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> getAll();
    Customer saveCustomer(Customer customer);
    boolean existsCustomerByIdentificationNumber(String identificationNumber);
}
