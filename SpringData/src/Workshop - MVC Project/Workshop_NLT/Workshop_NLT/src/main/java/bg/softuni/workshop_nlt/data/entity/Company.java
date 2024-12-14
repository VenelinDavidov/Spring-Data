package bg.softuni.workshop_nlt.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String name;



}
