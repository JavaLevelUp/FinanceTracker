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
import java.time.LocalDateTime;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer bean_account_id;
    private Integer batch_id;
    private Integer category_id;
    private Boolean is_outgoing;
    private BigDecimal amount;
    private LocalDateTime transaction_time;
}
