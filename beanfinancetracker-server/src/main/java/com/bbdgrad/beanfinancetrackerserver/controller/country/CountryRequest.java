package com.bbdgrad.beanfinancetrackerserver.controller.country;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryRequest {
    private String name;
}
