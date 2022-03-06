package ro.fasttrackit.controller;

import org.springframework.web.bind.annotation.*;
import ro.fasttrackit.entity.Transaction;
import ro.fasttrackit.model.Type;
import ro.fasttrackit.service.TransactionService;

import java.util.List;
import java.util.Map;

@RequestMapping("transactions")
@RestController
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    List<Transaction> getTransaction(@RequestParam(required = false) Type type,
                                     @RequestParam(required = false) Double minAmount,
                                     @RequestParam(required = false) Double maxAmount) {
        return service.getTransactions(type, minAmount, maxAmount);
    }

    @GetMapping("/v2")
    List<Transaction> getTransactionV2(@RequestParam(required = false) Type type,
                                     @RequestParam(required = false) Double minAmount,
                                     @RequestParam(required = false) Double maxAmount) {
        return service.getTransactionsV2(type, minAmount, maxAmount);
    }

    @PostMapping
    Transaction addTransaction(@RequestBody Transaction transaction) {
        return service.addTransaction(transaction);
    }

    @PutMapping("/{id}")
    Transaction replaceTransactionById(@PathVariable int id,
                                       @RequestBody Transaction transaction) {
        return service.replaceTransactionById(id, transaction);
    }

    @DeleteMapping("/{id}")
    void deleteTransactionById(@PathVariable int id) {
        service.deleteTransactionById(id);
    }

    @GetMapping("/reports/type")
    Map<Type, List<Transaction>> groupByType() {
        return service.groupByType();
    }

    @GetMapping("/reports/product")
    Map<String, List<Transaction>> groupByProduct() {
        return service.groupByProduct();
    }
}
