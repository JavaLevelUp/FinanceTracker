package com.bbdgrad.beanfinancetrackerserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Bean {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer country_id;
}
