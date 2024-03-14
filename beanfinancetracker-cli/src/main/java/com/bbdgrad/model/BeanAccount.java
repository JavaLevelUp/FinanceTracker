package com.bbdgrad.model;

import java.math.BigDecimal;

public record BeanAccount(Integer id, Integer user_id, String account_name, BigDecimal current_balance) {
    public BeanAccount(Integer user_id, String account_name, BigDecimal current_balance) {
        this(null, user_id, account_name, current_balance);
    }
}
