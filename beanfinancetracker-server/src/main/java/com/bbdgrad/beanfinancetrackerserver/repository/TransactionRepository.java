package com.bbdgrad.beanfinancetrackerserver.repository;

import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.BeanAccount;
import com.bbdgrad.beanfinancetrackerserver.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query("SELECT b FROM Transaction b WHERE b.bean_account_id = ?1")
    Optional<List<Transaction>> findByBeanAccountId(Integer bean_account_id);
}
