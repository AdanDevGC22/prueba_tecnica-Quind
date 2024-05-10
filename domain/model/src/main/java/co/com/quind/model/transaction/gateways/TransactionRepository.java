package co.com.quind.model.transaction.gateways;

import co.com.quind.model.transaction.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
}
