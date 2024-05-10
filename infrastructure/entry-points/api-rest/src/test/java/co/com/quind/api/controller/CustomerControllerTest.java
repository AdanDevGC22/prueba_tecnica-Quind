package co.com.quind.api.controller;

import co.com.quind.api.dto.request.CustomerRequestDto;
import co.com.quind.api.dto.response.CustomerResponseDto;
import co.com.quind.model.customer.Customer;
import co.com.quind.usecase.customer.CustomerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerUseCase customerUseCase;

    @InjectMocks
    private CustomerController customerController;

    // Método auxiliar para crear un objeto Customer con los campos completos
    private Customer createCustomer(Long id, String identificationType, String identificationNumber, String names, String lastname) {
        return Customer.builder()
                .id(id)
                .identificationType(identificationType)
                .identificationNumber(identificationNumber)
                .names(names)
                .lastname(lastname)
                .email(names.toLowerCase() + "@example.com")
                .birthdate(LocalDate.of(1980, 1, 1))
                .creationDate(LocalDateTime.now().minusDays(5))
                .modificationDate(LocalDateTime.now())
                .products(Collections.emptyList())
                .build();
    }

    @Test
    void testGetAllSuccessful() {
        // Arrange
        Customer customer1 = createCustomer(1L, "ID", "1234567890", "Adan", "Gonzalez");
        Customer customer2 = createCustomer(2L, "ID", "0987654321", "Pedro", "Gomez");
        List<Customer> customersExpected = List.of(customer1, customer2);

        when(customerUseCase.getAll()).thenReturn(customersExpected);
        List<CustomerResponseDto> customerResponseDtos = customerController.getAll();

        verify(customerUseCase, times(1)).getAll();
        assertEquals(customerResponseDtos.get(0).getIdentificationNumber(), customersExpected.get(0).getIdentificationNumber());
        assertEquals(customerResponseDtos.get(0).getNames(), customersExpected.get(0).getNames());
        assertEquals(customerResponseDtos.get(0).getLastname(), customersExpected.get(0).getLastname());
        assertEquals(customerResponseDtos.get(0).getIdentificationType(), customersExpected.get(0).getIdentificationType());
        assertEquals(customerResponseDtos.get(0).getIdentificationNumber(), customersExpected.get(0).getIdentificationNumber());
        assertEquals(customerResponseDtos.get(0).getBirthdate(), customersExpected.get(0).getBirthdate());
        assertEquals(customerResponseDtos.get(0).getEmail(), customersExpected.get(0).getEmail());
    }

    @Test
    void testSavedSuccessful() {
        Customer customer = createCustomer(1L, "ID", "1234567890", "Adan", "Gonzalez");
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .names(customer.getNames())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .identificationType(customer.getIdentificationType())
                .identificationNumber(customer.getIdentificationNumber())
                .birthdate(customer.getBirthdate())
                .build();

        when(customerUseCase.save(argThat(customerMatch ->
                customerMatch.getLastname().equals(customer.getLastname()) &&
                        customerMatch.getEmail().equals(customer.getEmail())
        ))).thenReturn(customer);
        CustomerResponseDto customerResponseDto = customerController.save(customerRequestDto);

        verify(customerUseCase, times(1)).save(argThat(customerMatch ->
                customerMatch.getLastname().equals(customer.getLastname())));
        assertEquals(customerResponseDto.getIdentificationNumber(), customer.getIdentificationNumber());
    }

    @Test
    void testGetCustomerByIdSuccessful() {
        Customer customerExpected = createCustomer(1L, "ID", "1234567890", "Adan", "Gonzalez");
        Long id = 1L;

        when(customerUseCase.getCustomerByID(id)).thenReturn(customerExpected);

        CustomerResponseDto customerResponseDto = customerController.getCustomerById(id);

        verify(customerUseCase, times(1)).getCustomerByID(id);
        assertEquals(customerResponseDto.getIdentificationNumber(), customerExpected.getIdentificationNumber());

    }

    @Test
    void testUpdateCustomerSuccessful() {
        Customer customer = createCustomer(1L, "ID", "1234567890", "Adan", "Gonzalez");
        CustomerRequestDto customerRequestDto = CustomerRequestDto.builder()
                .names(customer.getNames())
                .lastname(customer.getLastname())
                .email(customer.getEmail())
                .identificationType(customer.getIdentificationType())
                .identificationNumber(customer.getIdentificationNumber())
                .birthdate(customer.getBirthdate())
                .build();

        when(customerUseCase.update(argThat(customerMatch ->
                customerMatch.getLastname().equals(customer.getLastname()) &&
                        customerMatch.getEmail().equals(customer.getEmail())
        ))).thenReturn(customer);
        CustomerResponseDto customerResponseDto = customerController.updateCustomer(customerRequestDto);

        verify(customerUseCase, times(1)).update(argThat(customerMatch ->
                customerMatch.getLastname().equals(customer.getLastname())));
        assertEquals(customerResponseDto.getIdentificationNumber(), customer.getIdentificationNumber());
    }

    @Test
    void deleteCustomerFromDB_Success() {
        Long customerId = 1L;

        customerController.deleteCustomerFromDB(customerId);

        verify(customerUseCase, times(1)).deleteCustomerFromDB(customerId);
    }
}