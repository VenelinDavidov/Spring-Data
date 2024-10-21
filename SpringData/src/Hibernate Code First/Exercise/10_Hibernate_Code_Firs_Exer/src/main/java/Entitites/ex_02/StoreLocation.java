package Entitites.ex_02;

import Entitites.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store_location")
public class StoreLocation  extends BaseEntity {


    @Column(name ="location_name", nullable = false)
    private String locationName;

    @OneToMany(mappedBy = "location")
    private Set <Sale> sale;
}
