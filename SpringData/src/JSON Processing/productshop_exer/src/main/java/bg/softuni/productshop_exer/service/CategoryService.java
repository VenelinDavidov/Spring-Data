package bg.softuni.productshop_exer.service;

import bg.softuni.productshop_exer.service.dtos.export.CategoryByProductsDto;

import java.io.IOException;
import java.util.List;

public interface CategoryService {



    void seedCategories() throws IOException;


    List<CategoryByProductsDto> getAllCategoriesByProducts();

    void printAllCategoriesByProducts();

}
