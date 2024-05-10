package co.com.quind.api.controller;

import co.com.quind.api.dto.response.TransactionResponseDto;
import co.com.quind.api.mapper.TransactionMapper;
import co.com.quind.usecase.transaction.TransactionUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Transactional
@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransactionController {

    private TransactionUseCase transactionUseCase;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    public TransactionResponseDto transfer(@RequestParam(name = "sourceAccountNumber") String sourceAccountNumber, @RequestParam(name = "destinationAccountNumber") String destinationAccountNumber, @RequestParam(name = "amount") double amount) {
        return TransactionMapper.toTransactionResponseDto(transactionUseCase.transfer(sourceAccountNumber, destinationAccountNumber, amount));

    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/consignment")
    public TransactionResponseDto consignment(@RequestParam(name = "accountNumber") String accountNumber, @RequestParam(name = "amount") double amount) {
        return TransactionMapper.toTransactionResponseDto(transactionUseCase.consignment(accountNumber, amount));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/retreat")
    public TransactionResponseDto retreat(@RequestParam(name = "accountNumber") String accountNumber, @RequestParam(name = "amount") double amount) {
        return TransactionMapper.toTransactionResponseDto(transactionUseCase.retreat(accountNumber, amount));
    }


}
