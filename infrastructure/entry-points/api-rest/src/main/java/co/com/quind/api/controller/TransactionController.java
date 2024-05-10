package co.com.quind.api.controller;

import co.com.quind.api.dto.response.TransactionResponseDto;
import co.com.quind.api.mapper.TransactionMapper;
import co.com.quind.usecase.transaction.TransactionUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Transactional
@RestController
@RequestMapping(value = "/api/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class TransactionController {

    private TransactionUseCase transactionUseCase;
    @PostMapping("/")
    public ResponseEntity<TransactionResponseDto> transfer(@RequestParam(name = "sourceAccountNumber") String sourceAccountNumber, @RequestParam(name = "destinationAccountNumber") String destinationAccountNumber, @RequestParam(name = "amount") double amount) {
        TransactionResponseDto transactionResponseDto =TransactionMapper.toTransactionResponseDto(transactionUseCase.transfer(sourceAccountNumber,destinationAccountNumber,amount));
        return ResponseEntity.ok(transactionResponseDto);

    }

    @PostMapping("/consignment")
    public ResponseEntity<TransactionResponseDto> consignment(@RequestParam(name = "accountNumber") String accountNumber, @RequestParam(name = "amount") double amount) {
        TransactionResponseDto transactionResponseDto = TransactionMapper.toTransactionResponseDto(transactionUseCase.consignment(accountNumber,amount));
        return ResponseEntity.ok(transactionResponseDto);
    }

    @PostMapping("/retreat")
    public ResponseEntity<TransactionResponseDto> retreat(@RequestParam(name = "accountNumber") String accountNumber, @RequestParam(name = "amount") double amount) {
        TransactionResponseDto transactionResponseDto =TransactionMapper.toTransactionResponseDto(transactionUseCase.retreat(accountNumber,amount));
        return ResponseEntity.ok(transactionResponseDto);
    }


}
