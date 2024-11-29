package softuni.exam.models.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Country extends BaseEntity {


    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String currency;

    @OneToMany(mappedBy = "country")
    private Set<Person> people;

    @OneToMany(mappedBy = "country")
    private Set<Company>companies;
}
