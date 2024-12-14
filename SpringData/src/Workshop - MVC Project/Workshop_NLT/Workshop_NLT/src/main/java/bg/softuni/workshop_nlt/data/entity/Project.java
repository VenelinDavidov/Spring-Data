package bg.softuni.workshop_nlt.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_finished")
    private boolean isFinished;

    @Column(precision = 19, scale = 2)
    private BigDecimal payment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private Company company;




    @Override
    public String toString() {
        return String.format("Project name: %s\n" +
                             "Description: %s\n" +
                             "%.2f"
                ,this.name, this.description, this.payment);


    }
}
