package Entitites.ex_02;

import Entitites.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "sale")
public class Sale extends BaseEntity {

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product products;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customers;

    @ManyToOne
    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
    private StoreLocation location;
}
