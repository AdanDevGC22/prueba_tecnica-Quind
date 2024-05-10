package co.com.quind.jpa.mapper;

import co.com.quind.jpa.entities.CustomerEntity;
import co.com.quind.model.customer.Customer;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomerMapper {

    public static CustomerEntity toCustomerEntity(Customer customer){

        return CustomerEntity.builder()
                .id(customer.getId())
                .identificationType(customer.getIdentificationType())
                .identificationNumber(customer.getIdentificationNumber())
                .names(customer.getNames())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .birthdate(customer.getBirthdate())
                .creationDate(customer.getCreationDate())
                .modificationDate(customer.getModificationDate())
                .build();
    }

    public static  Customer toCustomer(CustomerEntity customerEntity){

        return Customer.builder()
                .id(customerEntity.getId())
                .identificationType(customerEntity.getIdentificationType())
                .identificationNumber(customerEntity.getIdentificationNumber())
                .names(customerEntity.getNames())
                .lastname(customerEntity.getLastname())
                .email(customerEntity.getEmail())
                .birthdate(customerEntity.getBirthdate())
                .creationDate(customerEntity.getCreationDate())
                .modificationDate(customerEntity.getModificationDate())
                .build();
    }

}
