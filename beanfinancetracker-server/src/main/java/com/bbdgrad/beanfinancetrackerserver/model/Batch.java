package com.bbdgrad.beanfinancetrackerserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Batch {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime batch_date;

    private Float weight;

    private Integer bean_id;
}
