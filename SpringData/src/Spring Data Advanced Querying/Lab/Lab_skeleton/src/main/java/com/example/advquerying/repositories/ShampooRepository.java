package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository <Shampoo, Long> {

    Set <Shampoo> findAllBySizeOrderById(Size size);

    Set <Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, long id);

    Set <Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Set <Shampoo> findAllByPriceLessThan(BigDecimal price);


    @Query("SELECT s FROM Shampoo s " +
            "JOIN s.ingredients i " +
            "WHERE i.name IN (:names)")
    Set <Shampoo> findAllByIngredientsNameIn(@Param(value = "names") List <String> names);

    @Query("FROM Shampoo  WHERE ingredients.size < :number")
    Set <Shampoo> findAllWithIngredientCountLesserThan(int number);
}
