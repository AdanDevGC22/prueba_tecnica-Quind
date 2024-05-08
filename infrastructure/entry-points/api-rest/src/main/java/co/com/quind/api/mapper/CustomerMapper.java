package co.com.quind.api.mapper;

import co.com.quind.api.dto.CustomerResponseDto;
import co.com.quind.model.customer.Customer;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static List<CustomerResponseDto> customerListToCustomerResponseDtoList(List<Customer> customerList){

        return customerList.stream().map(CustomerMapper::customerToCustomerResponseDto).collect(Collectors.toList());
    }

    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer){

        return CustomerResponseDto.builder()
                .names(customer.getNames())
                .email(customer.getEmail())
                .identificationNumber(customer.getIdentificationNumber())
                .birthdate(customer.getBirthdate())
                .identificationType(customer.getIdentificationType())
                .lastname(customer.getLastname())
                .build();
    }
}
