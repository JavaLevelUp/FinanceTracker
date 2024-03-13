package com.bbdgrad.beanfinancetrackerserver.controller.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BeanRequest {
    private String name;

    private Integer country_id;
}
