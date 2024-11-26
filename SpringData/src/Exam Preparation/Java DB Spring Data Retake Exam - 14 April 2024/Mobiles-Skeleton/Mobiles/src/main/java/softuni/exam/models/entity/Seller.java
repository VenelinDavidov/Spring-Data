package softuni.exam.models.entity;

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
@NoArgsConstructor
@Entity
@Table(name = "sellers")
public class Seller extends BaseEntity {


    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", unique = true, nullable = false)
    private String lastName;

    @Column(name = "personal_number ", unique = true, nullable = false)
    private String personalNumber;

    @OneToMany(mappedBy = "seller")
    private Set<Sale> sale;
}
