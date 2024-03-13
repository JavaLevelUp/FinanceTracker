package com.bbdgrad.beanfinancetrackerserver.controller.batch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BatchRequest {
    private Integer quantity;

    private Float weight;

    private Integer bean_id;
}
