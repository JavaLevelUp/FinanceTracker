package com.bbdgrad.model;

public record Student(Long id, String name, Integer age, String dob, String email) {

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dob='" + dob + '\'' +
                ", email='" + email + '\'';
    }
}
