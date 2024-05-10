package co.com.quind.usecase.transaction;

import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import co.com.quind.model.transaction.Transaction;
import co.com.quind.model.transaction.TransactionType;
import co.com.quind.model.transaction.gateways.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class TransactionUseCase {

    private final TransactionRepository transactionRepository;
    private final ProductRepository productRepository;
    public Transaction consignment(String accountNumber, double amount) {
        Product productDB = productRepository.getProductByAccountNumber(accountNumber);
        if (amount <= 0) {
            throw new QuindException(ErrorCode.B409009);
        }
        productDB.setBalance(productDB.getBalance().add(BigDecimal.valueOf(amount)));
        productDB.setModificationDate(LocalDateTime.now());
        productRepository.save(productDB);

        return saveTransaction(productDB, null, amount, TransactionType.CONSIGNACION);
    }

    public Transaction retreat(String accountNumber, double amount) {
        Product productDB = productRepository.getProductByAccountNumber(accountNumber);
        if (amount <= 0) {
            throw new QuindException(ErrorCode.B409009);
        }
        if (productDB.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new QuindException(ErrorCode.B409010);
        }

        productDB.setBalance(productDB.getBalance().subtract(BigDecimal.valueOf(amount)));
        productDB.setModificationDate(LocalDateTime.now());
        productRepository.save(productDB);
        return saveTransaction(productDB, null, amount, TransactionType.RETIRO);
    }

    public Transaction transfer(String sourceAccount, String destinationAccount, double amount) {
        Product sourceProductDB = productRepository.getProductByAccountNumber(sourceAccount);
        Product destinationProductDB = productRepository.getProductByAccountNumber(destinationAccount);

        if (amount <= 0) {
            throw new QuindException(ErrorCode.B409009);
        }
        if (sourceProductDB.getBalance().compareTo(BigDecimal.valueOf(amount)) < 0) {
            throw new QuindException(ErrorCode.B409010);
        }
        sourceProductDB.setBalance(sourceProductDB.getBalance().subtract(BigDecimal.valueOf(amount)));
        sourceProductDB.setModificationDate(LocalDateTime.now());
        destinationProductDB.setBalance(destinationProductDB.getBalance().add(BigDecimal.valueOf(amount)));
        destinationProductDB.setModificationDate(LocalDateTime.now());
        productRepository.save(sourceProductDB);
        productRepository.save(destinationProductDB);
        return saveTransaction(sourceProductDB, destinationProductDB, amount, TransactionType.TRANSFERENCIA);
    }

    private Transaction saveTransaction(Product sourceAccount, Product destinationAccount, double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setType(type);
        transaction.setAmount(BigDecimal.valueOf(amount));
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        return transactionRepository.save(transaction);
    }
}
