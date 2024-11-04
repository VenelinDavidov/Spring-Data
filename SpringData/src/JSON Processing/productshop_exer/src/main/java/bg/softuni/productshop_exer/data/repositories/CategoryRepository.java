package bg.softuni.productshop_exer.data.repositories;

import bg.softuni.productshop_exer.data.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "From Category c Order By size(c.products) Desc")
    Set<Category> findAllCategoriesByProducts();


}
