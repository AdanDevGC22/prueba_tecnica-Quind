package co.com.quind.model.product.gateways;

import co.com.quind.model.customer.Customer;
import co.com.quind.model.product.Product;

import java.util.List;

public interface ProductRepository {
    Product save(Product product);
    Product getProductByAccountNumber(String accountNumber);

    void delete(Product product);
    List<Product> getAllByCustomer(Customer customer);
}
