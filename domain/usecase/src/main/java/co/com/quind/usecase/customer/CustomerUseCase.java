package co.com.quind.usecase.customer;

import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.gateways.CustomerRepository;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomerUseCase {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

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
        customer.setCreationDate(LocalDateTime.now());
        customer.setModificationDate(LocalDateTime.now());
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
        Customer customer = getCustomerByID(id);
        List<Product> productList = productRepository.getAllByCustomer(customer);
        if (!productList.isEmpty()) {
            throw new QuindException(ErrorCode.B409006);
        }
        customerRepository.delete(id);
    }

    public Customer update(Customer customer) {
        // Lanzar una excepción si customer es nulo
        Objects.requireNonNull(customer);

        // Validar la edad del cliente
        int ageCustomer = LocalDate.now().getYear() - Optional.ofNullable(customer.getBirthdate())
                .map(LocalDate::getYear).orElseThrow(() -> new QuindException(ErrorCode.B400000));

        if (ageCustomer < 18) {
            throw new QuindException(ErrorCode.B409000);
        }

        // Buscar el cliente en el repositorio
        Customer customerSaved = Optional.ofNullable(customerRepository.findByIdentificationNumberAndIdentificationType(customer.getIdentificationNumber(),
                customer.getIdentificationType())).orElseThrow(() -> new QuindException(ErrorCode.B404000));

        // Actualizar los datos del cliente
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
