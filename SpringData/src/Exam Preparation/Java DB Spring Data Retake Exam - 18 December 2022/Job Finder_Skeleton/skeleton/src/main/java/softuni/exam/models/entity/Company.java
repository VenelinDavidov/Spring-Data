package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String  website;

    @Column(name = "date_establised",nullable = false)
    private LocalDate dateEstablished;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @OneToMany (mappedBy = "company")
    private Set<Job> job;

    @ManyToMany
    @JoinTable(name = "companies_job",
    joinColumns = @JoinColumn(name = "company_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "jobs_id", referencedColumnName = "id"))
    private Set<Job> jobs = new HashSet<>();
}
