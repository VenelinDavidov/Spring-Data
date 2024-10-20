import composition.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class CompositionMain {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("code_first_relation");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        // one to one
        CompositionCar car = new CompositionCar("Skoda", 50000);
        CompositionCar car2 = new CompositionCar("Dacia", 25000);
        CompositionCar car3 = new CompositionCar("Opel", 40000);


        PlateNumber number = new PlateNumber("4645", LocalDate.now());
        PlateNumber number1 = new PlateNumber("5350", LocalDate.now());
        PlateNumber number2 = new PlateNumber("8580", LocalDate.now());

        car.setPlateNumber(number);
        car2.setPlateNumber(number1);
        car3.setPlateNumber(number2);


        // many to many
        Course course = new Course("English", "Tosho Toshev");
        Student student = new Student("Ivan", 5);
        entityManager.persist(course);
        entityManager.persist(student);




        entityManager.persist(car);
        entityManager.persist(number);
        entityManager.persist(car2);
        entityManager.persist(number1);
        entityManager.persist(car3);
        entityManager.persist(number2);

        CompositionCar compositionCar = entityManager.find(CompositionCar.class, 9);
        PlateNumber plateNumber = entityManager.find(PlateNumber.class, 9);


        entityManager.getTransaction().commit();


    }
}
