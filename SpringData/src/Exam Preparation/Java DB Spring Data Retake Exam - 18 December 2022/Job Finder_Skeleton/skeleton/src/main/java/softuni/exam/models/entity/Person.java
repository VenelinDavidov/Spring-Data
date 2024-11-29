package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.StatusType;

import javax.persistence.*;
import javax.validation.constraints.Email;
@Getter
@Setter
@Entity
@Table(name = "people")
public class Person extends BaseEntity {

    @Column(name = "first_name", nullable = false,unique = true)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    @Email
    private String email;

    @Column( unique = true)
    private String phone;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_tupe")
    private StatusType statusType;

    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "id")
    private Country country;
}
