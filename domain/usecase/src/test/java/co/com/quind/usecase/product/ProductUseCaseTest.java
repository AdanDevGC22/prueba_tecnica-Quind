package co.com.quind.usecase.product;

import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.product.AccountStatus;
import co.com.quind.model.product.AccountType;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import co.com.quind.usecase.customer.CustomerUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductUseCaseTest {
    @Mock
    private CustomerUseCase customerUseCase;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductUseCase productUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_ValidProduct() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        Product productToSave = new Product();
        productToSave.setCustomer(customer);
        productToSave.setAccountType(AccountType.CORRIENTE);
        productToSave.setBalance(BigDecimal.valueOf(1000));
        productToSave.setStatus(AccountStatus.ACTIVA);
        productToSave.setAccountNumber("12222");

        when(customerUseCase.getCustomerByID(customerId)).thenReturn(customer);
        when(productRepository.save(any(Product.class))).thenReturn(productToSave);

        // Act
        Product savedProduct = productUseCase.save(productToSave);

        // Assert
        assertNotNull(savedProduct);
        assertEquals(customerId, savedProduct.getCustomer().getId());
        assertEquals(AccountStatus.ACTIVA, savedProduct.getStatus());
        assertNotNull(savedProduct.getAccountNumber());
    }

    @Test
    void testSave_CustomerNotFound() {
        // Arrange
        Long customerId = 1L;
        Product productToSave = new Product();
        productToSave.setCustomer(new Customer());
        productToSave.getCustomer().setId(customerId);
        when(customerUseCase.getCustomerByID(customerId)).thenReturn(null);

        // Act & Assert
        QuindException exception = assertThrows(QuindException.class, () -> productUseCase.save(productToSave));
        assertEquals(ErrorCode.B404000, exception.getError());
    }
    @Test
    void testGetProductByAccountNumber() {
        // Arrange
        String accountNumber = "123456789";
        Product expectedProduct = new Product();
        when(productRepository.getProductByAccountNumber(accountNumber)).thenReturn(expectedProduct);

        // Act
        Product result = productUseCase.getProductByAccountNumber(accountNumber);

        // Assert
        assertNotNull(result);
        assertEquals(expectedProduct, result);
    }
    @Test
    void testUpdate() {
        // Arrange
        Product productToUpdate = new Product();
        productToUpdate.setAccountNumber("123456789");
        Product productFromDB = new Product();
        when(productRepository.getProductByAccountNumber(productToUpdate.getAccountNumber())).thenReturn(productFromDB);
        when(productRepository.save(productFromDB)).thenReturn(productFromDB);

        // Act
        Product updatedProduct = productUseCase.update(productToUpdate);

        // Assert
        assertNotNull(updatedProduct);
        assertEquals(productFromDB.getStatus(), updatedProduct.getStatus());
        assertEquals(productFromDB.isExemptGMF(), updatedProduct.isExemptGMF());
        assertNotEquals(productToUpdate.getModificationDate(), updatedProduct.getModificationDate());
    }
    @Test
    void testDelete_ProductWithNonZeroBalance() {
        // Arrange
        String accountNumber = "123456789";
        Product product = new Product();
        product.setBalance(BigDecimal.valueOf(100)); // Non-zero balance
        when(productRepository.getProductByAccountNumber(accountNumber)).thenReturn(product);

        // Act & Assert
        QuindException exception = assertThrows(QuindException.class, () -> productUseCase.delete(accountNumber));
        assertEquals(ErrorCode.B409005, exception.getError());
    }
    @Test
    void testDelete_ProductWithZeroBalance() {
        // Arrange
        String accountNumber = "123456789";
        Product product = new Product();
        product.setBalance(BigDecimal.valueOf(0)); // Zero balance
        when(productRepository.getProductByAccountNumber(accountNumber)).thenReturn(product);

        // Act
        assertDoesNotThrow(() -> productUseCase.delete(accountNumber));
        verify(productRepository, times(1)).delete(product);
    }

}