package com.bbdgrad.beanfinancetrackerserver.controller.transaction;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Integer user_id;
    private Integer bean_account_id;
    private Integer batch_id;
    private Integer category_id;
    private Boolean is_outgoing;
    private BigDecimal amount;
    private String transaction_time;
}
