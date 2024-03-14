package com.bbdgrad.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Transaction(Integer id, Integer bean_account_id, String batch_id, Integer category_id
        , Boolean is_outgoing
        , BigDecimal amount
        , String transaction_time) {
    public Transaction(Integer bean_account_id, String batch_id, Integer category_id
          , Boolean is_outgoing
          , BigDecimal amount
          , String transaction_time) {
        this(null, bean_account_id, batch_id, category_id, is_outgoing, amount, transaction_time);
    }
}
