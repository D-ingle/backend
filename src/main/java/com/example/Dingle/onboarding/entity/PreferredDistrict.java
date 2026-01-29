package com.example.Dingle.onboarding.entity;

import com.example.Dingle.district.entity.District;
import com.example.Dingle.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferred_districts")
@Getter
@NoArgsConstructor
public class PreferredDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public PreferredDistrict(User user, District district) {
        this.user = user;
        this.district = district;
    }
}