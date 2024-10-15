package bg.softuni;


import Entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory ("hibernate-jpa");
        EntityManager entityManager = emf.createEntityManager ();
        entityManager.getTransaction ().begin ();


       Student student = new Student ("Tosho", 20, "School_Vratsa");
       entityManager.persist (student);

//         Student student = new Student ();
//         student.setName ("Ivo");
//         entityManager.persist (student);

        Student findStudent = entityManager.find (Student.class, 5);
        entityManager.remove (findStudent);
        System.out.println (findStudent.getId ());


        List <Student> from_student = entityManager
                .createQuery("FROM Student", Student.class)
                .getResultList();

        from_student.forEach(s -> System.out.println(s));


        entityManager.getTransaction ().commit ();


    }
}