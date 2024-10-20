package Inheritence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
@DiscriminatorValue("pepsi")
public class Car extends Vehicle {


    private static final String TYPE = "Car";
    @Column(name = "seat_count")
    private int seatCount;


    public Car() {

    }

    public Car(int seatCount) {
        super(TYPE);
        this.seatCount = seatCount;
    }


    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}
