package co.com.quind.jpa.mapper;

import co.com.quind.jpa.entities.TransactionEntity;
import co.com.quind.jpa.entities.TransactionType;
import co.com.quind.model.transaction.Transaction;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TransactionMapper {

    public static TransactionEntity toTransactionEntity(Transaction transaction){

        return TransactionEntity.builder()
                .type(TransactionType.valueOf(transaction.getType().name()))
                .amount(transaction.getAmount())
                .transactionDate(LocalDateTime.now())
                .sourceAccount(ProductMapper.toProductEntity(transaction.getSourceAccount()))
                .destinationAccount(transaction.getDestinationAccount()==null ? null : ProductMapper.toProductEntity(transaction.getDestinationAccount()))
                .build();
    }
    public static Transaction toTransaction(TransactionEntity transactionEntity){

        return Transaction.builder()
                .id(transactionEntity.getId())
                .type(co.com.quind.model.transaction.TransactionType.valueOf(transactionEntity.getType().name()))
                .amount(transactionEntity.getAmount())
                .transactionDate(LocalDateTime.now())
                .sourceAccount(ProductMapper.toProduct(transactionEntity.getSourceAccount()))
                .destinationAccount(transactionEntity.getDestinationAccount()==null ? null : ProductMapper.toProduct(transactionEntity.getDestinationAccount()))
                .build();
    }
}
