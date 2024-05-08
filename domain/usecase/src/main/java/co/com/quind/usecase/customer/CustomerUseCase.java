package co.com.quind.usecase.customer;

import co.com.quind.model.customer.Customer;
import co.com.quind.model.customer.gateways.CustomerRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CustomerUseCase {

    private final CustomerRepository customerRepository;
    public List<Customer> getAll(){
        return customerRepository.getAll();
    }

    public Customer save(Customer customer){

        LocalDate currentDate = LocalDate.now();
        LocalDate date18YearsAfter = customer.getBirthdate().plusYears(18);

        // Verificar si la fecha actual es después de 18 años de tu cumpleaños
        if (!currentDate.isAfter(date18YearsAfter)) {
            System.out.println("No han pasado exactamente 18 años desde tu cumpleaños.");
        }
        return  customerRepository.saveCustomer(customer);
    }
}
