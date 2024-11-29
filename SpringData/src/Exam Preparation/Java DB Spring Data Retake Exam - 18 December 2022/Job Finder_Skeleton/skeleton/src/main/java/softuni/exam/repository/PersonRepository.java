package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Person;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository  extends JpaRepository<Person, Long> {

    Optional<Person> findByFirstNameOrEmailOrPhone(@Size(min = 2, max = 30) String firstName, @Email String email, @Size(min = 2, max = 13) String phoneNumber);


}
