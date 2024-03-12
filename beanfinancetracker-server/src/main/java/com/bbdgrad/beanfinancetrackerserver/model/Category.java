package com.bbdgrad.beanfinancetrackerserver.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_seq",
            sequenceName = "category_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_seq"
    )
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "monthly_budget")
    private BigDecimal monthlyBudget;
}
