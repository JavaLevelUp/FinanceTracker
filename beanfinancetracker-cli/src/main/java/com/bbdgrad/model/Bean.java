package com.bbdgrad.model;

public record Bean(Integer id, String name, Integer country_id) {
    public Bean(String name, Integer country_id) {
        this(null, name, country_id);
    }
}
