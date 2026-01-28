package com.example.Dingle.property.entity;

import com.example.Dingle.property.type.OptionType;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "options")
@Getter
public class Option {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OptionType optionType;
}
