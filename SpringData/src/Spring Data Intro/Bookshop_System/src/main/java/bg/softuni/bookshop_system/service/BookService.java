package bg.softuni.bookshop_system.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List <String> findAllBooksAfterYear2000();

    List<String> findAllByBooksByGeorgePowellOrdered();

    void createBook();
}
