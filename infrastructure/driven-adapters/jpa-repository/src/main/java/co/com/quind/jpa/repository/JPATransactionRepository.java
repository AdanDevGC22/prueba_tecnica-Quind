package co.com.quind.jpa.repository;

import co.com.quind.jpa.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPATransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
