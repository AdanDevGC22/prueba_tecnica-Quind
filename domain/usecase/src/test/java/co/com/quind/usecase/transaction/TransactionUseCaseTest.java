package co.com.quind.usecase.transaction;

import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import co.com.quind.model.transaction.Transaction;
import co.com.quind.model.transaction.TransactionType;
import co.com.quind.model.transaction.gateways.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionUseCaseTest {
@Mock
private TransactionRepository transactionRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private TransactionUseCase transactionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConsignment() {
        // Arrange
        String accountNumber = "12345";
        double amount = 1000.0;
        Product product = new Product();
        product.setBalance(BigDecimal.valueOf(5000.0));
        product.setAccountNumber(accountNumber);
        when(productRepository.getProductByAccountNumber(accountNumber)).thenReturn(product);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transaction transaction = transactionUseCase.consignment(accountNumber, amount);

        // Assert
        assertNotNull(transaction);
        assertEquals(TransactionType.CONSIGNACION, transaction.getType());
        assertEquals(BigDecimal.valueOf(amount), transaction.getAmount());
        assertEquals(accountNumber, transaction.getSourceAccount().getAccountNumber());
        verify(productRepository).save(product);
    }

    @Test
    void testRetreatInsufficientFunds() {
        // Arrange
        String accountNumber = "12345";
        double amount = 6000.0;
        Product product = new Product();
        product.setBalance(BigDecimal.valueOf(5000.0));
        product.setAccountNumber(accountNumber);
        when(productRepository.getProductByAccountNumber(accountNumber)).thenReturn(product);

        // Act & Assert
        QuindException exception = assertThrows(QuindException.class, () -> transactionUseCase.retreat(accountNumber, amount));
        assertEquals(ErrorCode.B409010, exception.getError());
    }

    @Test
    void testTransfer() {
        // Arrange
        String sourceAccount = "12345";
        String destinationAccount = "67890";
        double amount = 1000.0;
        Product sourceProduct = new Product();
        sourceProduct.setBalance(BigDecimal.valueOf(5000.0));
        sourceProduct.setAccountNumber(sourceAccount);
        Product destinationProduct = new Product();
        destinationProduct.setBalance(BigDecimal.valueOf(2000.0));
        destinationProduct.setAccountNumber(destinationAccount);
        when(productRepository.getProductByAccountNumber(sourceAccount)).thenReturn(sourceProduct);
        when(productRepository.getProductByAccountNumber(destinationAccount)).thenReturn(destinationProduct);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transaction transaction = transactionUseCase.transfer(sourceAccount, destinationAccount, amount);

        // Assert
        assertNotNull(transaction);
        assertEquals(TransactionType.TRANSFERENCIA, transaction.getType());
        assertEquals(BigDecimal.valueOf(amount), transaction.getAmount());
        assertEquals(sourceAccount, transaction.getSourceAccount().getAccountNumber());
        assertEquals(destinationAccount, transaction.getDestinationAccount().getAccountNumber());
        verify(productRepository).save(sourceProduct);
        verify(productRepository).save(destinationProduct);
    }
    @Test
    void testSaveTransaction() {
        // Arrange
        Product sourceProduct = new Product();
        sourceProduct.setAccountNumber("12345");
        sourceProduct.setBalance(BigDecimal.valueOf(5000.0));

        Product destinationProduct = new Product();
        destinationProduct.setAccountNumber("67890");
        destinationProduct.setBalance(BigDecimal.valueOf(2000.0));

        double amount = 1000.0;
        TransactionType transactionType = TransactionType.TRANSFERENCIA;

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transaction transaction = transactionUseCase.saveTransaction(sourceProduct, destinationProduct, amount, transactionType);

        // Assert
        assertNotNull(transaction);
        assertEquals(transactionType, transaction.getType());
        assertEquals(BigDecimal.valueOf(amount), transaction.getAmount());
        assertEquals(sourceProduct.getAccountNumber(), transaction.getSourceAccount().getAccountNumber());
        assertEquals(destinationProduct.getAccountNumber(), transaction.getDestinationAccount().getAccountNumber());
        assertNotNull(transaction.getTransactionDate());

        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
}