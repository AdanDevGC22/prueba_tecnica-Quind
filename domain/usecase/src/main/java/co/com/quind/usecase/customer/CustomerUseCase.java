package co.com.quind.usecase.customer;

import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.config.ErrorCode;
import co.com.quind.model.customer.config.QuindException;
import co.com.quind.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CustomerUseCase {

    private final CustomerRepository customerRepository;
    public List<Customer> getAll(){
        return customerRepository.getAll();
    }

    public Customer save(Customer customer){

        validateIdentificationNumber(customer.getIdentificationNumber());
        LocalDate currentDate = LocalDate.now();
        int ageCustomer = currentDate.getYear()-customer.getBirthdate().getYear();

        if (ageCustomer < 18) {
            throw new QuindException(ErrorCode.B409000);
        }
        return  customerRepository.saveCustomer(customer);
    }

    public void validateIdentificationNumber(String identificationNumber){
        if (customerRepository.existsCustomerByIdentificationNumber(identificationNumber)){
            throw new QuindException(ErrorCode.B409001);
        }
    }
}
