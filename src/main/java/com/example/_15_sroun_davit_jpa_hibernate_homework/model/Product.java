package com.example._15_sroun_davit_jpa_hibernate_homework.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(length = 100, nullable = false)
    private String name;
    @Column(precision = 8, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer quantity;

}
