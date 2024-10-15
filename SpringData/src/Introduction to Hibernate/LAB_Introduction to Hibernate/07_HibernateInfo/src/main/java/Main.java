import entities.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        Configuration configuration = new Configuration ();
        configuration.configure ();

        SessionFactory sessionFactory = configuration.buildSessionFactory ();
        Session session = sessionFactory.openSession ();

        session.beginTransaction ();

//        Student student = new Student ();
//        student.setName ("Tosho");
//        session.save (student);
//
//        Student student = session.get (Student.class, 1);
//        System.out.printf ("%s-%s", student.getId (), student.getName ());

        List <Student> students = session.createQuery ("FROM Student AS s WHERE s.name = 'Tosho'", Student.class).list ();
        
        for (Student current : students) {
            System.out.println (current);
        }

        session.getTransaction ().commit ();
        session.close ();


    }

}
