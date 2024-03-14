package com.bbdgrad.beanfinancetrackerserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeanAccount {
    @Id
    @GeneratedValue
    private Integer id;

    private Integer user_id;

    private String account_name;

    private BigDecimal current_balance;

}
