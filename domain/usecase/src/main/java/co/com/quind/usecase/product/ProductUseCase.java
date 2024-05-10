package co.com.quind.usecase.product;

import co.com.quind.model.config.ErrorCode;
import co.com.quind.model.config.QuindException;
import co.com.quind.model.customer.Customer;
import co.com.quind.model.product.AccountStatus;
import co.com.quind.model.product.AccountType;
import co.com.quind.model.product.Product;
import co.com.quind.model.product.gateways.ProductRepository;
import co.com.quind.usecase.customer.CustomerUseCase;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class ProductUseCase {
    private final CustomerUseCase customerUseCase;
    private final ProductRepository productRepository;
    private Random random = new Random();

    public Product save(Product product){
        Customer customerDB = customerUseCase.getCustomerByID(product.getCustomer().getId());
        if(customerDB== null){
            throw new QuindException(ErrorCode.B404000);
        }
        String accountNumber = generateAccountNumber(product.getAccountType());
        Product account =Product.builder()
                .accountType(product.getAccountType())
                .accountNumber(accountNumber)
                .status(AccountStatus.ACTIVA)
                .balance(product.getBalance())
                .exemptGMF(product.isExemptGMF())
                .creationDate(LocalDateTime.now())
                .modificationDate(LocalDateTime.now())
                .customer(customerDB)
                .build();

        return productRepository.save(account);
    }


    private String generateAccountNumber(AccountType accountType) {
        String prefijo;
        if (accountType == AccountType.CORRIENTE) {
            prefijo = "33";
        } else {
            prefijo = "53";
        }

        // Generar sufijo aleatorio de 8 dígitos
        long sufijoLong = random.nextLong() % 100000000;
        if (sufijoLong < 0) {
            sufijoLong *= -1; // Asegurar que sea positivo
        }
        String sufijo = String.format("%08d", sufijoLong);

        return prefijo + sufijo;
    }

    public Product getProductByAccountNumber(String accountNumber) {
        return productRepository.getProductByAccountNumber(accountNumber);
    }

    public Product update(Product product){
        Product productDB = getProductByAccountNumber(product.getAccountNumber());

        productDB.setStatus(product.getStatus() != null ? product.getStatus() : productDB.getStatus());
        productDB.setExemptGMF(product.isExemptGMF());
        productDB.setModificationDate(LocalDateTime.now());

        return productRepository.save(productDB);
    }

    public void delete(String accountNumber) {
       Product product = getProductByAccountNumber(accountNumber);
       if (product.getBalance().intValue() != 0){
           throw new QuindException(ErrorCode.B409005);
       }
        productRepository.delete(product);
    }
}

