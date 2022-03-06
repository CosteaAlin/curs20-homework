package ro.fasttrackit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.fasttrackit.entity.Transaction;
import ro.fasttrackit.model.Type;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAll();

    List<Transaction> findByType(Type type);

    List<Transaction> findByAmountLessThan(Double amount);

    List<Transaction> findByAmountGreaterThan(Double amount);

    List<Transaction> findByTypeAndAmountLessThan(Type type, Double amount);

    List<Transaction> findByTypeAndAmountGreaterThan(Type type, Double amount);

    List<Transaction> findByAmountBetween(Double minAmount, Double maxAmount);

    List<Transaction> findByTypeAndAmountBetween(Type type,Double minAmount, Double maxAmount);

    void deleteById(int id);

    @Query("select t from Transaction t where (:type is null or t.type = :type)" +
            "and (:minAmount is null or t.amount > :minAmount)" +
            "and (:maxAmount is null or t.amount < :maxAmount)")
    List<Transaction> findWithFilters(@Param("type") Type type,
                                      @Param("minAmount") Double minAmount,
                                      @Param("maxAmount") Double maxAmount);
}
