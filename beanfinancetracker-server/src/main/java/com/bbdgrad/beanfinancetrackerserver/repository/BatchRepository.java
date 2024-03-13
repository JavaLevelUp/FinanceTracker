package com.bbdgrad.beanfinancetrackerserver.repository;

import com.bbdgrad.beanfinancetrackerserver.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Integer> {
}
