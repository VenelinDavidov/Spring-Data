package com.example.advquerying.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {

    List <String> getAllShampoosByGivenSize(String size);

    List<String> getAllShampoosContainingIngredient(List<String> ingredientNames);

    List<String> getAllShampoosByGivenSizeOrLabel(String size, long id);

    List<String> getAllShampoosWithPriceHigherThan(BigDecimal price);

    int countOfIngredientWithPriceLesserThan(BigDecimal price);

    Set <String> getAllShampoosWithCountOfIngredientsBelowNumber();
}
