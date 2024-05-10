package co.com.quind.api.controller;

import co.com.quind.api.dto.request.ProductRequestDto;
import co.com.quind.api.dto.request.UpdateProductRequestDto;
import co.com.quind.api.dto.response.ProductResponseDto;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.product.AccountStatus;
import co.com.quind.model.product.AccountType;
import co.com.quind.model.product.Product;
import co.com.quind.usecase.product.ProductUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductUseCase productUseCase;

    @InjectMocks
    private ProductController productController;
    @Test
    void testSaveProduct() {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setAccountType("CORRIENTE");
        requestDto.setBalance(BigDecimal.TEN);
        requestDto.setExemptGMF(true);
        requestDto.setCustomerId(1L);

        Customer customer = Customer.builder()
                .names("Adan")
                .lastname("Gonzalez")
                .build();

        Product product = Product.builder()
                .id(1L)
                .accountType(AccountType.CORRIENTE)
                .balance(BigDecimal.TEN)
                .exemptGMF(true)
                .status(AccountStatus.ACTIVA)
                .customer(customer)
                .build();

        when(productUseCase.save(any())).thenReturn(product);

        ProductResponseDto responseDto = productController.save(requestDto);

        assertEquals(product.getAccountType(), responseDto.getAccountType());
        assertEquals(product.getBalance(), responseDto.getBalance());
        assertEquals(product.isExemptGMF(), responseDto.isExemptGMF());
    }

    @Test
    void testUpdateProduct() {
        UpdateProductRequestDto updateProductRequestDto = new UpdateProductRequestDto();
        updateProductRequestDto.setAccountNumber("123456789");
        updateProductRequestDto.setStatus("ACTIVA");
        updateProductRequestDto.setExemptGMF(true);

        Customer customer = Customer.builder()
                .names("Adan")
                .lastname("Gonzalez")
                .build();

        Product product = Product.builder()
                .id(1L)
                .accountType(AccountType.CORRIENTE)
                .accountNumber(updateProductRequestDto.getAccountNumber())
                .status(AccountStatus.ACTIVA)
                .customer(customer)
                .exemptGMF(true)
                .build();

        when(productUseCase.update(any())).thenReturn(product);

        ProductResponseDto responseDto = productController.update(updateProductRequestDto);

        assertEquals(product.getAccountType(), responseDto.getAccountType());
        assertEquals(product.getStatus(), responseDto.getStatus());
        assertEquals(product.isExemptGMF(), responseDto.isExemptGMF());

        verify(productUseCase, times(1)).update(any());
    }

    @Test
    void testDeleteProduct() {

        String accountNumber = "123456789";

        productController.delete(accountNumber);

        verify(productUseCase, times(1)).delete(accountNumber);
    }

    @Test
    void testGetProductById() {
        // Arrange
        String accountNumber = "123456789";
        Customer customer = Customer.builder()
                .names("Adan")
                .lastname("Gonzalez")
                .build();

        Product product = Product.builder()
                .id(1L)
                .accountType(AccountType.CORRIENTE)
                .accountNumber(accountNumber)
                .status(AccountStatus.ACTIVA)
                .balance(BigDecimal.TEN)
                .exemptGMF(true)
                .customer(customer)
                .build();

        when(productUseCase.getProductByAccountNumber(accountNumber)).thenReturn(product);

        ProductResponseDto responseDto = productController.getProductById(accountNumber);

        assertEquals(product.getAccountType(), responseDto.getAccountType());
        assertEquals(product.getAccountNumber(), responseDto.getAccountNumber());
        assertEquals(product.getStatus(), responseDto.getStatus());
        assertEquals(product.getBalance(), responseDto.getBalance());
        assertEquals(product.isExemptGMF(), responseDto.isExemptGMF());

        verify(productUseCase, times(1)).getProductByAccountNumber(accountNumber);
    }

}