package com.example.Dingle.preference.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions")
@Getter
@NoArgsConstructor
public class Condition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String conditionName;

    public Condition(String conditionName) {
        this.conditionName = conditionName;
    }
}
