package co.com.quind.api.controller;

import co.com.quind.api.dto.response.TransactionResponseDto;
import co.com.quind.model.product.Product;
import co.com.quind.model.transaction.Transaction;
import co.com.quind.model.transaction.TransactionType;
import co.com.quind.usecase.transaction.TransactionUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionUseCase transactionUseCase;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void testTransfer() {
        // Arrange
        String sourceAccountNumber = "123456789";
        String destinationAccountNumber = "987654321";
        double amount = 100.0;

        Product sourceAccount = Product.builder().accountNumber(sourceAccountNumber).build();
        Product destinationAccount = Product.builder().accountNumber(destinationAccountNumber).build();

        Transaction transaction = Transaction.builder()
                .type(TransactionType.TRANSFERENCIA)
                .amount(BigDecimal.valueOf(amount))
                .transactionDate(LocalDateTime.now())
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .build();

        when(transactionUseCase.transfer(sourceAccountNumber, destinationAccountNumber, amount)).thenReturn(transaction);

        TransactionResponseDto responseDto = transactionController.transfer(sourceAccountNumber, destinationAccountNumber, amount);

        assertEquals(transaction.getType(), responseDto.getType());
        assertEquals(transaction.getAmount(), responseDto.getAmount());
        assertEquals(transaction.getSourceAccount().getAccountNumber(), responseDto.getSourceAccountNumber());
        assertEquals(transaction.getDestinationAccount().getAccountNumber(), responseDto.getDestinationAccountNumber());

        assertNotNull(responseDto.getTransactionDate());

        verify(transactionUseCase, times(1)).transfer(sourceAccountNumber, destinationAccountNumber, amount);

    }

    @Test
    void testConsignment() {
        String accountNumber = "123456789";
        double amount = 100.0;

        Transaction transaction = Transaction.builder()
                .id(1L)
                .type(TransactionType.CONSIGNACION)
                .amount(BigDecimal.valueOf(amount))
                .transactionDate(LocalDateTime.now())
                .sourceAccount(Product.builder().accountNumber(accountNumber).build())
                .build();
        when(transactionUseCase.consignment(accountNumber, amount)).thenReturn(transaction);

        TransactionResponseDto responseDto = transactionController.consignment(accountNumber, amount);

        assertNotNull(responseDto);
        assertEquals(accountNumber, responseDto.getSourceAccountNumber());
        assertEquals(amount, responseDto.getAmount().doubleValue());
    }

    @Test
    void testRetreat() {
        String accountNumber = "123456789";
        double amount = 100.0;

        Transaction transaction = Transaction.builder()
                .id(1L)
                .type(TransactionType.RETIRO)
                .amount(BigDecimal.valueOf(amount))
                .transactionDate(LocalDateTime.now())
                .sourceAccount(Product.builder().accountNumber(accountNumber).build())
                .build();
        when(transactionUseCase.retreat(accountNumber, amount)).thenReturn(transaction);

        TransactionResponseDto responseDto = transactionController.retreat(accountNumber, amount);

        assertNotNull(responseDto);
        assertEquals(accountNumber, responseDto.getSourceAccountNumber());
        assertEquals(amount, responseDto.getAmount().doubleValue());
    }

}