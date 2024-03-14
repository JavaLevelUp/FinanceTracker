package com.bbdgrad.beanfinancetrackerserver.controller.transaction;

import com.bbdgrad.beanfinancetrackerserver.model.Transaction;
import com.bbdgrad.beanfinancetrackerserver.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping()
    public ResponseEntity<List<Transaction>> getTransactions() {
        return transactionService.getTransactions();
    }

    @PostMapping
    public ResponseEntity<Transaction> registerTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.registerTransaction(transactionRequest);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable("transactionId") Integer transactionId) {
        return transactionService.getTransaction(transactionId);
    }

    @DeleteMapping("/delete/{transactionId}")
    public ResponseEntity<String> removeTransaction(@PathVariable("transactionId") Integer transactionId) {
        return transactionService.removeTransaction(transactionId);
    }

    @PutMapping("/update/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("transactionId") Integer transactionId,
                                                   @RequestParam(required = false) Optional<Integer> user_id,
                                                   @RequestParam(required = false) Optional<Integer> bean_account_id,
                                                   @RequestParam(required = false) Optional<Integer> batch_id,
                                                   @RequestParam(required = false) Optional<Integer> category_id,
                                                   @RequestParam(required = false) Optional<Boolean> is_outgoing,
                                                   @RequestParam(required = false) Optional<BigDecimal> amount,
                                                   @RequestParam(required = false) Optional<String> transaction) {
        return transactionService.updateTransaction(transactionId, user_id, bean_account_id, batch_id, category_id, is_outgoing, amount, transaction);
    }

}
