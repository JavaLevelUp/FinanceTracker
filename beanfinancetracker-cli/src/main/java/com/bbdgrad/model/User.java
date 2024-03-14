package com.bbdgrad.model;

public record User(Integer id, String name, String email) {
    public User(String name, String email) {
        this(null, name, email);
    }
}
