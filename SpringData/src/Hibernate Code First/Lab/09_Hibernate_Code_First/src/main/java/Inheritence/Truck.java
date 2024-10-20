package Inheritence;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "trucks")
public class Truck extends TransportationVehicle {

    public static final String TYPE = "Truck";

    public Truck() {

    }

    public Truck(int numOfContainers) {
        super(numOfContainers);
    }

}
