package com.bbdgrad.model;

public record Country(Integer id, String name) {
    public Country(String name) {
        this(null, name);
    }
}
