package co.com.quind.api.mapper;

import co.com.quind.api.dto.response.TransactionResponseDto;
import co.com.quind.model.transaction.Transaction;
import co.com.quind.model.transaction.TransactionType;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TransactionMapper {
    public static TransactionResponseDto toTransactionResponseDto(Transaction transaction){

        return TransactionResponseDto.builder()
                .type(TransactionType.valueOf(transaction.getType().name()))
                .amount(transaction.getAmount())
                .transactionDate(LocalDateTime.now())
                .sourceAccountNumber(transaction.getSourceAccount().getAccountNumber())
                .destinationAccountNumber(transaction.getDestinationAccount() == null ? null : transaction.getDestinationAccount().getAccountNumber())
                .build();
    }
}
