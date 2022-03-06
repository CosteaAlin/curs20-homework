package ro.fasttrackit.service;

import org.springframework.stereotype.Service;
import ro.fasttrackit.entity.Transaction;
import ro.fasttrackit.exceptions.ResourceNotFoundException;
import ro.fasttrackit.model.Type;
import ro.fasttrackit.repository.TransactionRepository;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getTransactions(Type type, Double minAmount, Double maxAmount) {
        if (type != null && minAmount != null && maxAmount != null) {
            return repository.findByTypeAndAmountBetween(type, minAmount, maxAmount);
        } else if (type != null && minAmount != null) {
            return repository.findByTypeAndAmountGreaterThan(type, minAmount);
        } else if (type != null && maxAmount != null) {
            return repository.findByTypeAndAmountLessThan(type, maxAmount);
        } else if (type != null) {
            return repository.findByType(type);
        } else if (minAmount != null && maxAmount != null) {
            return repository.findByAmountBetween(minAmount, maxAmount);
        } else if (minAmount != null){
            return repository.findByAmountGreaterThan(minAmount);
        } else if (maxAmount != null){
            return repository.findByAmountLessThan(maxAmount);
        }
        return repository.findAll();
    }

    public List<Transaction> getTransactionsV2(Type type, Double minAmount, Double maxAmount){
        return repository.findWithFilters(type,minAmount, maxAmount);
    }

    public Transaction addTransaction(Transaction transaction) {
        handleProductInput(transaction.getProduct());
        return repository.save(transaction);
    }

    public Transaction replaceTransactionById(int id, Transaction transaction) {
        handleProductInput(transaction.getProduct());
        Transaction transactionToUpdate = getTransactionById(id);
        transactionToUpdate.setProduct(transaction.getProduct());
        transactionToUpdate.setType(transaction.getType());
        transactionToUpdate.setAmount(transaction.getAmount());
        repository.save(transactionToUpdate);
        return transactionToUpdate;
    }

    public void deleteTransactionById(int id) {
        getTransactionById(id);
        repository.deleteById(id);
    }

    public Map<Type, List<Transaction>> groupByType() {
        return repository.findAll()
                .stream()
                .collect(groupingBy(Transaction::getType));
    }

    public Map<String, List<Transaction>> groupByProduct() {
        return repository.findAll()
                .stream()
                .collect(groupingBy(Transaction::getProduct));
    }

    private Transaction getTransactionById(int id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Can`t find transaction with id " + id));
    }

    private void handleProductInput(String product) {
        if (product == null || product.isEmpty()) {
            throw new InvalidParameterException("Product has to be set.");
        }
    }
}
