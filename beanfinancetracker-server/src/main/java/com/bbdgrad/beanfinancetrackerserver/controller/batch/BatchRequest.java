package com.bbdgrad.beanfinancetrackerserver.controller.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequest {
    private String batch_date;

    private Float weight;

    private Integer bean_id;
}
