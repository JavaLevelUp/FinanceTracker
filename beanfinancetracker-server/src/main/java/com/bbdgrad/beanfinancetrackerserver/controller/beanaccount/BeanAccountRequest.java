package com.bbdgrad.beanfinancetrackerserver.controller.beanaccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeanAccountRequest {
    private Integer user_id;

    private String account_name;

    private BigDecimal current_balance;
}
