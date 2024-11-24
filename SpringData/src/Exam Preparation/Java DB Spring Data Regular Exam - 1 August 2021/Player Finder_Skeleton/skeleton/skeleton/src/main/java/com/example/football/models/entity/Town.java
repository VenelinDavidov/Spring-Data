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
@Table(name = "towns")
public class Town extends BaseEntity {

    @Column( nullable = false, unique = true)
    private String name;

    @Column( nullable = false)
    private int population;

    @Column(name = "travel_guide", nullable = false, columnDefinition = "TEXT")
    private String travelGuide;

    @OneToMany(mappedBy = "town")
    private Set<Player> playerSet;
}
