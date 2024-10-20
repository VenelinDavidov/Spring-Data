import composition.Company;
import composition.Plane;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class PlaneMain {
    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("code_first_relation");

        EntityManager em = emf.createEntityManager();


        em.getTransaction().begin();

        // one to many
        Company company = new Company("Airline VD planes");

        Plane airplane = new Plane("Belgium", 20);
        Plane airplane2 = new Plane("Bulgaria", 25);
        Plane airplane3 = new Plane("Croatia", 15);

//        Plane airplane4 = em.find(Plane.class, 6);
//        Plane airplane5 = em.find(Plane.class, 7);
//        Plane airplane6 = em.find(Plane.class, 8);


        List<Plane> planes = new ArrayList<Plane>();
        planes.add(airplane);
        planes.add(airplane2);
        planes.add(airplane3);

        company.setPlaneList(planes);
        em.persist(company);



        em.getTransaction().commit();
        em.close();


    }
}
