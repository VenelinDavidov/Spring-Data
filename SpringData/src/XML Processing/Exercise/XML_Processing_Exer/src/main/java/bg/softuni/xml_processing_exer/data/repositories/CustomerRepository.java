package bg.softuni.xml_processing_exer.data.repositories;

import bg.softuni.xml_processing_exer.data.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Get all customers, ordered by their birthdate in ascending order
    //If two customers are born on the same date, first print those, who are not young drivers

    Set<Customer> findAllByOrderByBirthDateAscIsYoungDriverAsc();

    @Query(value = "From Customer Where SIZE(sales) > 0")
    Set<Customer> findAllWithBoughtCars();

}
