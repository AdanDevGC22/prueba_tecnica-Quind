package co.com.quind.api;
import co.com.quind.api.dto.CustomerRequestDto;
import co.com.quind.api.dto.CustomerResponseDto;
import co.com.quind.api.mapper.CustomerMapper;
import co.com.quind.usecase.customer.CustomerUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customer", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CustomerController {
    private final CustomerUseCase customerUseCase;


    @GetMapping
    public List<CustomerResponseDto> getAll() {

        return CustomerMapper.customerListToCustomerResponseDtoList(customerUseCase.getAll());
    }

    @PostMapping
    public CustomerResponseDto save(@RequestBody CustomerRequestDto customerRequestDto){
        return  null;
    }
}
