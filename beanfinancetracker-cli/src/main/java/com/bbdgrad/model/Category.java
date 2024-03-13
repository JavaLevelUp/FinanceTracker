package com.bbdgrad.model;

import java.math.BigDecimal;

public record Category(Integer id, String name, BigDecimal monthlyBudget) {
    public Category(String name, BigDecimal monthlyBudget) {
        this(null, name, monthlyBudget);
    }
}
