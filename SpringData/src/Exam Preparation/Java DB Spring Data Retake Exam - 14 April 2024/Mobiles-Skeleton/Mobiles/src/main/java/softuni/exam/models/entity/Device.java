package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.entity.enums.DeviceType;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "devices")
public class Device extends BaseEntity {

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "device_type ")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @Column(name = "model", unique = true, nullable = false)
    private String model;

    @Column(name = "price")
    private double price;

    @Column
    private int storage;

    @ManyToOne
    @JoinColumn(name = "sale_id",referencedColumnName = "id")
    private Sale sale;
}
