package com.bbdgrad.beanfinancetrackerserver.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Country {
    @Id
    @SequenceGenerator(
            name = "country_seq",
            sequenceName = "country_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_seq"
    )
    private Integer id;

    private String name;
}
