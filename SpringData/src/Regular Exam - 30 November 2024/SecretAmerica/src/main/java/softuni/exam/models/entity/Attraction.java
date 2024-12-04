package softuni.exam.models.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "attractions")
public class Attraction extends BaseEntity {


    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "elevation", nullable = false)
    private int elevation;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "attraction")
    private Set<Visitor> visitors;
}
