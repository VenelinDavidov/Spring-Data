package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface IngredientRepository extends JpaRepository <Ingredient, Long> {

    Set <Ingredient> findAllByNameStartingWith(String symbol);


    //delete
    @Transactional
    @Modifying
    @Query("DELETE Ingredient WHERE name = :name")
    int deleteIngredientByName(String name);

    //update
    @Modifying
    @Transactional
    @Query("UPDATE Ingredient SET price = price * :percent")
    int updateAllByPrice(BigDecimal percent);
    // Update Ingredient Set price = price * :price


    //update for names
    @Modifying
    @Transactional
    @Query("UPDATE Ingredient SET price = price * :percent WHERE name IN :names")
    int updateAllByPriceForGivenNames(BigDecimal percent, @Param (value = "names") List<String> strings);

}
