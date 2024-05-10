package co.com.quind.usecase.customer;

import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.gateways.CustomerRepository;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerUseCaseTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CustomerUseCase customerUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer_ValidCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setBirthdate(LocalDate.of(2000, 1, 1));
        when(customerRepository.saveCustomer(any(Customer.class))).thenReturn(customer);
        when(customerRepository.existsCustomerByIdentificationNumber(anyString())).thenReturn(false);

        // Act
        Customer savedCustomer = customerUseCase.save(customer);

        // Assert
        assertNotNull(savedCustomer);
        assertEquals(LocalDate.now(), savedCustomer.getCreationDate().toLocalDate());
        assertEquals(LocalDate.now(), savedCustomer.getModificationDate().toLocalDate());
    }

    @Test
    void testSaveCustomer_UnderageCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setBirthdate(LocalDate.now());

        // Act & Assert
        assertThrows(QuindException.class, () -> customerUseCase.save(customer));
    }

    @Test
    void testSaveCustomer_DuplicateIdentificationNumber() {
        // Arrange
        Customer customer = new Customer();
        customer.setBirthdate(LocalDate.of(2000, 1, 1));
        String identificationNumber = "123456789";
        customer.setIdentificationNumber(identificationNumber);
        when(customerRepository.existsCustomerByIdentificationNumber(identificationNumber)).thenReturn(true);

        // Act & Assert
        assertThrows(QuindException.class, () -> customerUseCase.save(customer));
    }

    @Test
    void testDeleteCustomerFromDB_CustomerWithProducts() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.getCustomerById(customerId)).thenReturn(customer);
        when(productRepository.getAllByCustomer(customer)).thenReturn(Collections.singletonList(new Product()));

        // Act & Assert
        assertThrows(QuindException.class, () -> customerUseCase.deleteCustomerFromDB(customerId));
        verify(customerRepository, never()).delete(anyLong());
    }

    @Test
    void testDeleteCustomerFromDB_CustomerWithoutProducts() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        when(customerRepository.getCustomerById(customerId)).thenReturn(customer);
        when(productRepository.getAllByCustomer(customer)).thenReturn(Collections.emptyList());

        // Act
        customerUseCase.deleteCustomerFromDB(customerId);

        // Assert
        verify(customerRepository).delete(customerId);
    }

}
