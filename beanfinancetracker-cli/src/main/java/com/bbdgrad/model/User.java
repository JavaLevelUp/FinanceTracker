package com.bbdgrad.model;

public record User(String name, String email, String password, String role) {
    public User(String email, String password) {
        this(null, email, password, null);
    }
}
