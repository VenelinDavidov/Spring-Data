package bg.softuni.workshop_nlt.data.repositories;

import bg.softuni.workshop_nlt.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByAgeGreaterThan(int age);

}
