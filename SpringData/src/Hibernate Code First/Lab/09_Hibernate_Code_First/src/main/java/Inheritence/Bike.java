package Inheritence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.Table;

@Entity
@Table(name = "bikes")
public class Bike extends Vehicle {

    private static final String TYPE = "bike";

    @Column(name = "wheel_size")
    private int wheelSize;

    public Bike() {

    }

    public Bike(int wheelSize) {
        super(TYPE);
        this.wheelSize = wheelSize;
    }

    public Bike(String type, int wheelSize) {
        super(type);
        this.wheelSize = wheelSize;
    }

    public int getWheelSize() {
        return wheelSize;
    }

    public void setWheelSize(int wheelSize) {
        this.wheelSize = wheelSize;
    }
}
