package co.com.quind.api.controller;

import co.com.quind.api.dto.request.ProductRequestDto;
import co.com.quind.api.dto.request.UpdateProductRequestDto;
import co.com.quind.api.dto.response.ProductResponseDto;
import co.com.quind.api.mapper.ProductMapper;
import co.com.quind.model.product.Product;
import co.com.quind.usecase.product.ProductUseCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
@Transactional
@RestController
@RequestMapping(value = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ProductController {
    private final ProductUseCase productUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto productRequestDto){
        Product productSaved = productUseCase.save(ProductMapper.toDomain(productRequestDto));

        return  ProductMapper.toProductResponseDto(productSaved);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{accountNumber}")
    public ProductResponseDto getProductById(@PathVariable(name = "accountNumber") String accountNumber){
        return ProductMapper.toProductResponseDto(productUseCase.getProductByAccountNumber(accountNumber));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/")
    public ProductResponseDto update(@RequestBody UpdateProductRequestDto updateProductRequestDto){

        return ProductMapper.toProductResponseDto(productUseCase.update(ProductMapper.updateToDamin(updateProductRequestDto)));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{accountNumber}")
    public void delete(@PathVariable(name = "accountNumber") String accountNumber){
        productUseCase.delete(accountNumber);
    }
}
