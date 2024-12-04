package softuni.exam.models.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "visitors")
public class Visitor extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "attraction_id",referencedColumnName = "id",nullable = false)
    private Attraction attraction;

    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id",nullable = false)
    private Country country;

    @OneToOne
    @JoinColumn(name = "personal_data_id",referencedColumnName = "id",nullable = false)
    private PersonalData personalData;

}
