package com.example.Dingle.onboarding.entity;

import com.example.Dingle.preference.entity.Condition;
import com.example.Dingle.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferred_conditions")
@Getter
@NoArgsConstructor
public class PreferredCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id", nullable = false)
    private Condition condition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public PreferredCondition(UserEntity user, Condition condition) {
        this.user = user;
        this.condition = condition;
    }
}