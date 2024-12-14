package bg.softuni.workshop_nlt.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;


    @Override
    public String toString() {
        return String.format("Name: %s %s\n" +
                             "Age: %d\n" +
                             "Project name: %s",
                this.firstName, this.lastName, this.age, this.project.getName());
    }
}
