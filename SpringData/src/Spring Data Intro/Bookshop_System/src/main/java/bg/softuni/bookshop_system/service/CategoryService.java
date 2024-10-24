package bg.softuni.bookshop_system.service;

import bg.softuni.bookshop_system.data.entities.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {

    void  seedCategories() throws IOException;

    Set<Category> getRandomCategories();
}
