package bg.softuni.bookshop_system.service.impl;

import bg.softuni.bookshop_system.data.entities.Author;
import bg.softuni.bookshop_system.data.entities.Book;
import bg.softuni.bookshop_system.data.entities.Category;
import bg.softuni.bookshop_system.data.entities.enums.AgeRestriction;
import bg.softuni.bookshop_system.data.entities.enums.EditionType;
import bg.softuni.bookshop_system.data.repositories.BookRepository;
import bg.softuni.bookshop_system.service.AuthorService;
import bg.softuni.bookshop_system.service.BookService;
import bg.softuni.bookshop_system.service.CategoryService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String FILE_PATH = "src/main/resources/files/books.txt";


    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookRepository bookRepository;

    private Validator validator;

    public BookServiceImpl(CategoryService categoryService, AuthorService authorService, BookRepository bookRepository) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator ())
                .buildValidatorFactory()
                .getValidator();
    }




    @Override
    public void seedBooks() throws IOException {

        if (this.bookRepository.count () == 0) {
            Files.readAllLines (Path.of (FILE_PATH))
                    .stream ()
                    .filter (row -> !row.trim ().isEmpty ())
                    .forEach (row -> {

                        //2 12/3/1993 9998 5.26 2 Wilderness!
                        String[] data = row.split ("\\s+");

                        Author author = this.authorService.getRandomAuthor ();
                        EditionType editionType = EditionType.values ()[Integer.parseInt (data[0])];
                        LocalDate releaseDate = LocalDate.parse (data[1],
                                DateTimeFormatter.ofPattern ("d/M/yyyy"));
                        int copies = Integer.parseInt (data[2]);
                        BigDecimal price = new BigDecimal (data[3]);
                        AgeRestriction ageRestriction = AgeRestriction
                                .values ()[Integer.parseInt (data[4])];
                        String title = Arrays.stream (data)
                                .skip (5)
                                .collect (Collectors.joining (" "));
                        Set <Category> categories = this.categoryService.getRandomCategories ();


                        Book book = new Book (title, editionType, price, copies, releaseDate,
                                ageRestriction, author, categories);

                        this.bookRepository.saveAndFlush (book);

                    });
        }
    }

    @Override
    public List <String> findAllBooksAfterYear2000() {
        return this.bookRepository.findAllByReleaseDateAfter (LocalDate.of (2000, 12, 31))
                .stream ()
                .map(Book::getTitle)
                .collect (Collectors.toList ());
    }

    @Override
    public List <String> findAllByBooksByGeorgePowellOrdered() {
        return this.bookRepository
                .findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle ("George", "Powell")
                .stream ()
                .map (book -> String.format ("%s %s %d",
                        book.getTitle (),
                        book.getReleaseDate (),
                        book.getCopies ()))
                .collect (Collectors.toList ());
    }

    // Validator factory methods
    @Override
    public void createBook() {

        Book notValidBook = new Book (null, EditionType.NORMAL, BigDecimal.ONE, 5, LocalDate.now (), AgeRestriction.ADULT,
                this.authorService.getRandomAuthor (), this.categoryService.getRandomCategories ());

        Book validBook   = new Book ("Vampires", EditionType.NORMAL, BigDecimal.ONE, 1000, LocalDate.now (), AgeRestriction.ADULT,
                this.authorService.getRandomAuthor (), this.categoryService.getRandomCategories ());

        Set <ConstraintViolation <Book>> validateNotValid = this.validator.validate (notValidBook);
        Set <ConstraintViolation <Book>> validateBook = this.validator.validate (validBook);

        if (validateBook.isEmpty ()){
            this.bookRepository.saveAndFlush (validBook);
        }
        System.out.println ();
    }
}
