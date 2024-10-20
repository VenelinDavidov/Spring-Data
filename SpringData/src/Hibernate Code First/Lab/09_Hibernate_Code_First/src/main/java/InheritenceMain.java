import Inheritence.Bike;
import Inheritence.Car;
import Inheritence.Truck;
import net.bytebuddy.build.Plugin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class InheritenceMain    {
    public static void main(String[] args) {

        EntityManagerFactory  entityManagerFactory =
                Persistence.createEntityManagerFactory("code_first_relation");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(new Car(5));
        entityManager.persist(new Truck(5));
        entityManager.persist(new Bike(10));


        Car car = entityManager.find(Car.class, 1);
        Car car1 = entityManager.find(Car.class, 2);




        entityManager.getTransaction().commit();
        entityManager.close();

    }
}
