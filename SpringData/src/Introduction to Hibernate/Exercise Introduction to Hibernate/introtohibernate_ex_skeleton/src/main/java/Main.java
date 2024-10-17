import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final Scanner scanner = new Scanner (System.in);
//   private static final  BufferedReader bf = new BufferedReader (new InputStreamReader (System.in));


    public static void main(String[] args) throws IOException {

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory ("softuni_jpa");
        EntityManager entityManager = emf.createEntityManager ();

        entityManager.getTransaction ().begin ();

//          ex_01 (entityManager);
//          ex_02(entityManager);
//          ex_03 (entityManager);
//          ex_04 (entityManager);
//          ex_05 (entityManager);
//          ex_06(entityManager);
//          ex_07 (entityManager);
        //         ex_08 (entityManager);
//          ex_09 (entityManager);
//          ex_10 (entityManager);
//          ex_11 (entityManager);
        ex_12 (entityManager);

        entityManager.getTransaction ().commit ();
    }

    private static void ex_12(EntityManager entityManager) {

        List <Town> towns = entityManager.createQuery ("FROM Town WHERE name = :name", Town.class)
                .setParameter ("name", scanner.nextLine ())
                .getResultList ();

        if (!towns.isEmpty ()) {

            Town town = towns.get (0);

            List <Address> addresses = entityManager.createQuery ("SELECT a FROM Address a " +
                            " JOIN a.town t WHERE t.name = :name ", Address.class)
                    .setParameter ("name", town.getName ())
                    .getResultList ();

            addresses.forEach (address -> {
                address.getEmployees ().forEach (employee -> {
                    employee.setAddress (null);
                    entityManager.persist (employee);
                });
                entityManager.remove (address);
            });

            System.out.printf ("%d addresses in %s deleted", addresses.size (), town.getName ());
            entityManager.remove (town);
        }


    }

    private static void ex_11(EntityManager entityManager) {
//
//        entityManager
//                .createQuery("SELECT CONCAT(e.department.name, ' ', MAX(e.salary)) " +
//                        "FROM Employee e " +
//                        "GROUP BY e.department.id " +
//                        "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000", String.class)
//                .getResultList()
//                .forEach(System.out::println);


        // two solution

        List <Department> fromDepartment = entityManager.createQuery ("FROM Department", Department.class).getResultList ();
        fromDepartment.forEach (department -> {
            double departmentMaxSalary = department.getEmployees ()
                    .stream ()
                    .mapToDouble (e -> e.getSalary ().doubleValue ())
                    .max ().orElse (0);

            if (departmentMaxSalary < 30000 || departmentMaxSalary > 70000) {
                System.out.printf ("%s %.2f%n", department.getName (), departmentMaxSalary);
            }
        });
    }

    private static void ex_10(EntityManager entityManager) {
        entityManager.createQuery ("FROM Employee WHERE firstName LIKE CONCAT(:letters, '%')", Employee.class)
                .setParameter ("letters", scanner.nextLine ())
                .getResultStream ()
                .forEach (employee -> System.out.printf ("%s %s - %s - ($%.2f)%n", employee.getFirstName (), employee.getLastName (),
                        employee.getJobTitle (), employee.getSalary ()));

    }

    private static void ex_09(EntityManager entityManager) {

        List <Employee> employees = entityManager.createQuery ("SELECT e FROM Employee e " +
                        "JOIN e.department d " +
                        "WHERE d.name IN ( 'Engineering', 'Tool Design', 'Marketing', 'Information Services')", Employee.class)
                .getResultList ();

        for (Employee employee : employees) {
            employee.setSalary (employee.getSalary ().multiply (BigDecimal.valueOf (1.12)));
            entityManager.persist (employee);
            System.out.printf ("%s %s ($%.2f)%n", employee.getFirstName (), employee.getLastName (), employee.getSalary ());
        }


    }

    private static void ex_08(EntityManager entityManager) {

        entityManager.createQuery ("FROM Project ORDER BY startDate DESC", Project.class)
                .setMaxResults (10)
                .getResultStream ()
                .sorted (Comparator.comparing (Project::getName))
                .forEach (project -> System.out.printf ("Project name: %s%n" +
                                "Project Description: %s%n" +
                                "Project Start Date; %s%n" +
                                "Project End Date: %s%n", project.getName (), project.getDescription (),
                        project.getStartDate (), project.getEndDate ()));


    }

    private static void ex_07(EntityManager entityManager) {

        Employee employee = entityManager.createQuery
                        (" FROM Employee e WHERE e.id = ?1 ", Employee.class)
                .setParameter (1, Integer.parseInt (scanner.nextLine ()))
                .getSingleResult ();

        System.out.printf ("%s %s - %s%n", employee.getFirstName (), employee.getLastName (), employee.getJobTitle ());
        employee.getProjects ()
                .stream ()
                .sorted (Comparator.comparing (Project::getName))
                .forEach (project -> System.out.printf ("         %s%n", project.getName ()));

    }

    private static void ex_06(EntityManager entityManager) {
        entityManager.createQuery ("FROM Address ORDER BY employees.size DESC", Address.class)
                .setMaxResults (10)
                .getResultStream ()
                .forEach (a -> System.out.printf ("%s, %s - %d employees%n",
                        a.getText (),
                        a.getTown ().getName (),
                        a.getEmployees ().size ()));

    }

    private static void ex_05(EntityManager entityManager) {
        Town town = entityManager.find (Town.class, 32);
        Address address = new Address ();
        address.setText ("Vitoshka 15");
        address.setTown (town);
        entityManager.persist (address);

        String lastName = scanner.nextLine ();
        List <Employee> resultList = entityManager.createQuery
                        ("FROM Employee WHERE lastName =  :lastName", Employee.class)
                .setParameter ("lastName", lastName)
                .getResultList ();


        if (!resultList.isEmpty ()) {
            Employee employee = resultList.get (0);
            employee.setAddress (address);
            entityManager.persist (employee);
        }
    }

    private static void ex_04(EntityManager entityManager) {
        entityManager.createQuery
                        ("SELECT e FROM Employee e " +
                                        "JOIN e.department d" +
                                        " WHERE d.id = 6 " +
                                        "ORDER BY e.salary, e.id",
                                Employee.class)
                .getResultStream ()
                .forEach (e -> System.out.printf ("%s %s from Research and Development $%.2f%n",
                        e.getFirstName (),
                        e.getLastName (),
                        e.getSalary ()));


    }

    private static void ex_03(EntityManager entityManager) {
        entityManager.createQuery ("FROM Employee WHERE salary > 50000", Employee.class)
                .getResultStream ()
                .map (Employee::getFirstName)
                .forEach (System.out::println);
    }

    private static void ex_02(EntityManager entityManager) {

        List <Town> resultList = entityManager.createQuery
                        ("FROM Town WHERE LENGTH(name) > 5", Town.class)
                .getResultList ();
        resultList.forEach (town -> {
            town.setName (town.getName ().toUpperCase ());
            entityManager.persist (town);
        });

    }

    private static void ex_01(EntityManager entityManager) {

        String[] input = scanner.nextLine ().split ("\\s+");
        List <Employee> resultList = entityManager.createQuery
                        ("FROM Employee  WHERE  " +
                                        "firstName = :first_name AND " +
                                        "lastName = :last_name",
                                Employee.class)
                .setParameter ("first_name", input[0])
                .setParameter ("last_name", input[1])
                .getResultList ();

        System.out.println (resultList.size () > 0 ? "Yes" : "No");
    }
}
