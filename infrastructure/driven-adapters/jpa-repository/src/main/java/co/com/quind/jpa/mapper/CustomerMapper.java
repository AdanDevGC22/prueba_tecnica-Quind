package co.com.quind.jpa.mapper;

import co.com.quind.jpa.entities.CustomerEntity;
import co.com.quind.model.customer.Customer;

import java.time.LocalDateTime;

public class CustomerMapper {

    public static CustomerEntity toCustomerEntity(Customer customer){
        LocalDateTime date = LocalDateTime.now();

        return CustomerEntity.builder()
                .identificationType(customer.getIdentificationType())
                .identificationNumber(customer.getIdentificationNumber())
                .names(customer.getNames())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .birthdate(customer.getBirthdate())
                .creationDate(date)
                .modificationDate(date)
                .build();
    }

    public static  Customer toCustomer(CustomerEntity customerEntity){

        return Customer.builder()
                .identificationType(customerEntity.getIdentificationType())
                .identificationNumber(customerEntity.getIdentificationNumber())
                .names(customerEntity.getNames())
                .lastname(customerEntity.getLastname())
                .email(customerEntity.getEmail())
                .birthdate(customerEntity.getBirthdate())
                .modificationDate(customerEntity.getModificationDate())
                .build();
    }
}
