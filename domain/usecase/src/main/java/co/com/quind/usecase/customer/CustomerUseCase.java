package co.com.quind.usecase.customer;

import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.config.ErrorCode;
import co.com.quind.model.customer.config.QuindException;
import co.com.quind.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class CustomerUseCase {

    private final CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }

    public Customer save(Customer customer) {

        validateIdentificationNumber(customer.getIdentificationNumber());
        LocalDate currentDate = LocalDate.now();
        int ageCustomer = currentDate.getYear() - customer.getBirthdate().getYear();

        if (ageCustomer < 18) {
            throw new QuindException(ErrorCode.B409000);
        }
        return customerRepository.saveCustomer(customer);
    }

    public void validateIdentificationNumber(String identificationNumber) {
        if (customerRepository.existsCustomerByIdentificationNumber(identificationNumber)) {
            throw new QuindException(ErrorCode.B409001);
        }
    }

    public Customer getCustomerByID(Long id) {
        return customerRepository.getCustomerById(id);
    }

    public void deleteCustomerFromDB(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new QuindException(ErrorCode.B404000);
        }
        customerRepository.delete(id);
    }

    public Customer update(Customer customer) {
        LocalDate currentDate = LocalDate.now();
        int ageCustomer = currentDate.getYear() - customer.getBirthdate().getYear();

        if (ageCustomer < 18) {
            throw new QuindException(ErrorCode.B409000);
        }

        Customer customerSaved = customerRepository.findByIdentificationNumberAndIdentificationType(customer.getIdentificationNumber(), customer.getIdentificationType());

        if (Objects.isNull(customerSaved)) {

            throw new QuindException(ErrorCode.B404000);
        }

        customerSaved.setModificationDate(LocalDateTime.now());
        customerSaved.setEmail(customer.getEmail());
        customerSaved.setNames(customer.getNames());
        customerSaved.setLastname(customer.getLastname());
        customerSaved.setIdentificationType(customer.getIdentificationType());
        customerSaved.setIdentificationNumber(customer.getIdentificationNumber());
        customerSaved.setBirthdate(customer.getBirthdate());
        customerRepository.saveCustomer(customerSaved);

        return customerSaved;
    }
}
