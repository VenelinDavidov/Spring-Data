package bg.softuni.bookshop_system.data.repositories;

import bg.softuni.bookshop_system.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Integer> {

}
