package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "hoursaweek", nullable = false)
    private double hoursAWeek;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;

    @ManyToMany(mappedBy = "jobs")
    private Set<Company> companies;
}
