package bg.softuni.bookshop_system;

import bg.softuni.bookshop_system.service.AuthorService;
import bg.softuni.bookshop_system.service.BookService;
import bg.softuni.bookshop_system.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    public CommandLineRunnerImpl(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData ();
        //    getAllBooksAfter2000 ();
        //    getAllAuthorsFirstAndLastNameBeforeBooks1990 ();
        //    getAllAuthorsByBooksDesc ();
        //    printBooksByGeorgePowell ();
        this.bookService.createBook ();
    }

    //#5
    private void printBooksByGeorgePowell() {
        this.bookService.findAllByBooksByGeorgePowellOrdered ().forEach (System.out::println);
    }

    //#4
    private void getAllAuthorsByBooksDesc() {
        this.authorService.getAllAuthorsDescBooks ().forEach (System.out::println);
    }

    //#3
    private void getAllAuthorsFirstAndLastNameBeforeBooks1990() {
        this.authorService.getAllAuthorsFirstAndLastNameForBooksBeforeYear1990 ().forEach (System.out::println);
    }

    //#2
    private void getAllBooksAfter2000() {
        this.bookService.findAllBooksAfterYear2000 ().forEach (System.out::println);
    }

    //#1
    private void seedData() throws IOException {
        this.categoryService.seedCategories ();
        this.authorService.seedAuthors ();
        this.bookService.seedBooks ();
    }
}
