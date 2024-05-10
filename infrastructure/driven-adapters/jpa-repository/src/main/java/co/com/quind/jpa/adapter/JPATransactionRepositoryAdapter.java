package co.com.quind.jpa.adapter;

import co.com.quind.jpa.mapper.TransactionMapper;
import co.com.quind.jpa.repository.JPATransactionRepository;
import co.com.quind.model.transaction.Transaction;
import co.com.quind.model.transaction.gateways.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JPATransactionRepositoryAdapter implements TransactionRepository {

    private final JPATransactionRepository jpaTransactionRepository;
    @Override
    public Transaction save(Transaction transaction) {
        return TransactionMapper.toTransaction(jpaTransactionRepository.save(TransactionMapper.toTransactionEntity(transaction)));
    }
}
