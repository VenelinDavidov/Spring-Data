package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    @Column(name = "discounted")
    private boolean discount;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate ;

    @ManyToOne
    @JoinColumn(name = "seller_id",referencedColumnName = "id")
    private Seller seller;

    @OneToMany(mappedBy = "sale")
    private Set<Device> devices;
}
