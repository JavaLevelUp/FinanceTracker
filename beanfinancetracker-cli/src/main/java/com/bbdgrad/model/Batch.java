package com.bbdgrad.model;

import java.time.LocalDateTime;

public record Batch(Integer id, String batch_date, Float weight, Integer bean_id) {
    public Batch(String batch_date, Float weight, Integer bean_id) {
        this(null, batch_date, weight, bean_id);
    }
}
