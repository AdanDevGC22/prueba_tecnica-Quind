package co.com.quind.api.controller;
import co.com.quind.api.dto.request.CustomerRequestDto;
import co.com.quind.api.dto.response.CustomerResponseDto;
import co.com.quind.api.mapper.CustomerMapper;
import co.com.quind.model.customer.Customer;
import co.com.quind.usecase.customer.CustomerUseCase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomerController {
    private final CustomerUseCase customerUseCase;


    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CustomerResponseDto> getAll() {

        return CustomerMapper.customerListToCustomerResponseDtoList(customerUseCase.getAll());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponseDto save(@RequestBody @Valid CustomerRequestDto customerRequestDto){
        Customer customerSaved = customerUseCase.save(CustomerMapper.toDomain(customerRequestDto));

        return  CustomerMapper.customerToCustomerResponseDto(customerSaved);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public CustomerResponseDto getCustomerById(@PathVariable(name = "id") Long id){
        return CustomerMapper.customerToCustomerResponseDto(customerUseCase.getCustomerByID(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/")
    public CustomerResponseDto updateCustomer(@RequestBody CustomerRequestDto customerRequestDto){

        return CustomerMapper.customerToCustomerResponseDto(customerUseCase.update(CustomerMapper.toDomain(customerRequestDto)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteCustomerFromDB(@PathVariable(name = "id") Long id){
        //no puedo eliminarlo hasta que tenga elimine primero las cuentas
         customerUseCase.deleteCustomerFromDB(id);
    }

}
