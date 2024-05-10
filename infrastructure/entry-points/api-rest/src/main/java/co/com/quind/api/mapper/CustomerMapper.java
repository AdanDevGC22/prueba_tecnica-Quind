package co.com.quind.api.mapper;

import co.com.quind.api.dto.request.CustomerRequestDto;
import co.com.quind.api.dto.response.CustomerResponseDto;
import co.com.quind.model.customer.Customer;
import lombok.experimental.UtilityClass;

import java.util.List;
@UtilityClass
public class CustomerMapper {

    public static List<CustomerResponseDto> customerListToCustomerResponseDtoList(List<Customer> customerList){

        return customerList.stream().map(CustomerMapper::customerToCustomerResponseDto).toList();
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

    public static Customer toDomain(CustomerRequestDto customerRequestDto){

        return Customer.builder()
                .identificationType(customerRequestDto.getIdentificationType())
                .identificationNumber(customerRequestDto.getIdentificationNumber())
                .names(customerRequestDto.getNames())
                .lastname(customerRequestDto.getLastname())
                .email(customerRequestDto.getEmail())
                .birthdate(customerRequestDto.getBirthdate())
                .build();
    }

}
