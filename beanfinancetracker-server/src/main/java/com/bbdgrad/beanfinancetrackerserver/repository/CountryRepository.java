package com.bbdgrad.beanfinancetrackerserver.repository;

import com.bbdgrad.beanfinancetrackerserver.model.Bean;
import com.bbdgrad.beanfinancetrackerserver.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query("SELECT c FROM Country c WHERE c.name = ?1")
    Optional<Country> findByName(String name);
}
