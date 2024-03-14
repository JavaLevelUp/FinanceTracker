package com.bbdgrad.beanfinancetrackerserver.service;

import com.bbdgrad.beanfinancetrackerserver.controller.batch.BatchRequest;
import com.bbdgrad.beanfinancetrackerserver.controller.transaction.TransactionRequest;
import com.bbdgrad.beanfinancetrackerserver.model.*;
import com.bbdgrad.beanfinancetrackerserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final BeanAccountRepository beanAccountRepository;
    private final BatchRepository batchRepository;
    private final CategoryRepository categoryRepository;

    public ResponseEntity<List<Transaction>> getTransactionsFromUser(Integer userId) {
        if (userRepository.findById(userId).isEmpty()) {
            return new ResponseEntity("User does not exist", HttpStatus.NOT_FOUND);
        }
        Optional<List<BeanAccount>> beanAccountsList = beanAccountRepository.findByUserId(userId);
        List<Transaction> userTransactions = new ArrayList<>();
        beanAccountsList.ifPresent(accounts -> {
            for (BeanAccount account : accounts) {
                Optional<List<Transaction>> temp = transactionRepository.findByBeanAccountId(account.getId());
                temp.ifPresent(userTransactions::addAll);
            }
        });

        return ResponseEntity.ok().body(userTransactions);
    }

    public ResponseEntity<Transaction> registerTransaction(TransactionRequest transactionRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try {
            LocalDateTime dateTime = LocalDateTime.parse(transactionRequest.getTransaction_time(), formatter);
            if (beanAccountRepository.findById(transactionRequest.getBean_account_id()).isEmpty()) {
                return new ResponseEntity("Bean Account is not found", HttpStatus.NOT_FOUND);
            }
            if (batchRepository.findById(transactionRequest.getBatch_id()).isEmpty()) {
                return new ResponseEntity("Batch does noe exist", HttpStatus.NOT_FOUND);
            }
            if (categoryRepository.findById(transactionRequest.getCategory_id()).isEmpty()) {
                return new ResponseEntity("Category does note exist", HttpStatus.NOT_FOUND);
            }
            BeanAccount updateBeanAccount = beanAccountRepository.findById(transactionRequest.getBean_account_id()).get();
            BigDecimal oldCurrentAmount = updateBeanAccount.getCurrent_balance();
            BigDecimal newAmount = BigDecimal.ZERO;
            if(transactionRequest.getIs_outgoing()){
                newAmount = oldCurrentAmount.subtract(transactionRequest.getAmount());
            }else{
                newAmount = oldCurrentAmount.add(transactionRequest.getAmount());
            }

            updateBeanAccount.setCurrent_balance(newAmount);
            beanAccountRepository.save(updateBeanAccount);
            var newTrans = Transaction.builder()
                    .bean_account_id(transactionRequest.getBean_account_id())
                    .batch_id(transactionRequest.getBatch_id())
                    .category_id(transactionRequest.getCategory_id())
                    .is_outgoing(transactionRequest.getIs_outgoing())
                    .amount(transactionRequest.getAmount())
                    .transaction_time(dateTime)
                    .build();
            transactionRepository.save(newTrans);
            return ResponseEntity.ok().body(newTrans);
        } catch (Exception ex) {
            return new ResponseEntity("Date format error", HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<String> removeTransaction(Integer id) {
        Optional<Transaction> transactionExist = transactionRepository.findById(id);
        if (transactionExist.isEmpty()) {
            return new ResponseEntity<>("Transaction does not Exists", HttpStatus.NOT_FOUND);
        }
        transactionRepository.deleteById(id);

        return ResponseEntity.ok().body("Transaction removed successfully");
    }

    public ResponseEntity<Transaction> getTransaction(Integer id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            return new ResponseEntity("Transaction does not exist", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(transaction.orElse(null));
    }

    public ResponseEntity<Transaction> updateTransaction(Integer id,
                                                         Optional<Integer> bean_account_id,
                                                         Optional<Integer> batch_id,
                                                         Optional<Integer> category_id,
                                                         Optional<Boolean> is_outgoing,
                                                         Optional<BigDecimal> amount,
                                                         Optional<String> transaction_time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()) {
            return new ResponseEntity("Batch does not Exists", HttpStatus.NOT_FOUND);
        }
        if (transaction_time.isPresent()) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(transaction_time.get(), formatter);
                transaction_time.ifPresent(integer -> transaction.get().setTransaction_time(dateTime));
            } catch (Exception e) {
                return new ResponseEntity("Date format error", HttpStatus.BAD_REQUEST);

            }
        }

        if (bean_account_id.isPresent()) {
            if (beanAccountRepository.findById(bean_account_id.get()).isEmpty()) {
                return new ResponseEntity("Bean Account does not Exists", HttpStatus.NOT_FOUND);
            }
            transaction.get().setBean_account_id(bean_account_id.get());
        }
        if (batch_id.isPresent()) {
            if (batchRepository.findById(batch_id.get()).isEmpty()) {
                return new ResponseEntity("Batch does not Exists", HttpStatus.NOT_FOUND);
            }
            transaction.get().setBatch_id(batch_id.get());
        }
        if (category_id.isPresent()) {
            if (categoryRepository.findById(category_id.get()).isEmpty()) {
                return new ResponseEntity("Category does not Exists", HttpStatus.NOT_FOUND);
            }
            transaction.get().setCategory_id(category_id.get());
        }
        is_outgoing.ifPresent(aBoolean -> transaction.get().setIs_outgoing(aBoolean));
        amount.ifPresent(a -> transaction.get().setAmount(a));
        transactionRepository.save(transaction.get());
        return ResponseEntity.ok().body(transaction.get());

    }
}
