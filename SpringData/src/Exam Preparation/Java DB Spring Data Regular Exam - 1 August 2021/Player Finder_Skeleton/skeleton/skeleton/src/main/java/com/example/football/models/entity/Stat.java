package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(name = "stats")
public class Stat extends BaseEntity{

    @Column(name = "shooting", nullable = false)
    private float shooting;

    @Column(name = "passing", nullable = false)
    private float passing;

    @Column(name = "endurance", nullable = false)
    private float endurance;

    @OneToMany(mappedBy = "stat")
    private Set<Player> playerSet;
}
