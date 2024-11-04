package bg.softuni.productshop_exer.data.repositories;

import bg.softuni.productshop_exer.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Set<User> findAllBy();
}
