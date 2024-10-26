package com.example.advquerying.init;

import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    // fields
    private static final BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));
    private final ShampooService shampooService;
    private final IngredientService ingredientService;


    //constructors
    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {

//      lab_01 (reader);
//      lab_02 (reader);
//      lab_03 (reader);
//      lab_04 ();
//      lab_05 ();
//      lab_06 ();
//      lab_08 ();
//      lab_09 ();
//      lab_10 ();
        lab_11 ();
    }

    private void lab_11() {
        System.out.println (this.ingredientService.updatePricesForGivenNames ());
    }

    private void lab_10() {
        this.ingredientService.updatedIngredientPrices ();
    }

    private void lab_09() {
        System.out.println (this.ingredientService.deleteIngredientByName ("Nettle"));
    }

    private void lab_08() {
        System.out.println (this.shampooService.getAllShampoosWithCountOfIngredientsBelowNumber ());
    }

    private void lab_06() {
        System.out.println (this.shampooService.countOfIngredientWithPriceLesserThan (BigDecimal.valueOf (8.50)));
    }

    private void lab_05() {
        this.ingredientService.getAllIngredientWithStartingName ("M")
                .forEach (System.out::println);
    }

    private void lab_04() {
        this.shampooService.getAllShampoosWithPriceHigherThan (BigDecimal.valueOf (5))
                .forEach (System.out::println);
    }

    private void lab_03() {
        this.shampooService.getAllShampoosByGivenSizeOrLabel ("medium",10)
                .forEach (System.out::println);
    }

    private void lab_02(BufferedReader reader) throws IOException {

        List <String> ingredientNames = new ArrayList <> ();
        ingredientNames.add ("Berry");
        ingredientNames.add ("Mineral-Collagen");
        this.shampooService.getAllShampoosContainingIngredient (ingredientNames)
                .forEach (System.out::println);

//         Option #2
//        this.shampooService.getAllShampoosContainingIngredient (List.of (reader.readLine ().split ("\\s+")))
//                .forEach (System.out::println);
    }

    private void lab_01(BufferedReader reader) throws IOException {
        this.shampooService.getAllShampoosByGivenSize (reader.readLine ())
                .forEach (System.out::println);
    }
}
