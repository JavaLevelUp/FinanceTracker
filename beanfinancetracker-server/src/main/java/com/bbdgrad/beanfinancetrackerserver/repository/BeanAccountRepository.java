package com.bbdgrad.beanfinancetrackerserver.repository;

import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.BeanAccount;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeanAccountRepository extends JpaRepository<BeanAccount, Integer> {
    @Query("SELECT b FROM BeanAccount b WHERE b.user_id = ?1")
    Optional<List<BeanAccount>> findByUserId(Integer userId);
}
