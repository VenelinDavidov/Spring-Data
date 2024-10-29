package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    //field
    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    private static final Scanner scanner = new Scanner (System.in);

    // constructor
    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    //methods
    @Override
    public void run(String... args) throws Exception {
        seedData ();

//        printAllBooksAfterYear(2000);
//        printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
//        printAllAuthorsAndNumberOfTheirBooks();
//        pritnALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

//          Exercise_01 ();
//          Exercise_02 ();
//          Exercise_03 ();
//          Exercise_04();
//          Exercise_05();
//          Exercise_06();
//          Exercise_07();
//          Exercise_08 ();
//          Exercise_09 ();
//          Exercise_10 ();
//          Exercise_11 ();
//          Exercise_12();

    }

    private void Exercise_12() {
        bookService.sellCopies (1, 1200);
    }

    private void Exercise_11() {
        String title = scanner.nextLine ();
        this.bookService.findBookInformationForGivenTitle (title)
                .forEach (System.out::println);

    }

    private void Exercise_10() {

        String authorName = scanner.nextLine ();

        System.out.println (this.bookService.findTotalBookCopies (authorName));
    }

    private void Exercise_09() {
        int minLength = Integer.parseInt (scanner.nextLine ());
        int count = bookService.findTitleCountLongerThan (minLength);

        System.out.printf ("There are %d books with longer titles than %d symbols.", count, minLength);
    }

    private void Exercise_08() {
        String lastNameStart = scanner.nextLine ();
        this.bookService.findTitlesForAuthorNameStartingWith (lastNameStart);
    }

    private void Exercise_07() {
        String needle = scanner.nextLine ();

        bookService.findTitlesContaining (needle).forEach (System.out::println);
    }

    private void Exercise_06() {
        String ending = scanner.nextLine ();
        List <String> names = authorService.findAllByNamesEndingIn (ending);

        names.forEach (System.out::println);
    }

    //5
    private void Exercise_05() {
        String beforeDate = scanner.nextLine ();
        LocalDate parseData = LocalDate.parse (beforeDate, DateTimeFormatter.ofPattern ("dd-MM-yyyy"));

        this.bookService.findAllByReleaseDateBefore (parseData)
                .forEach (book -> System.out.printf ("%s %s $%.2f%n",
                        book.getTitle (),
                        book.getEditionType (),
                        book.getPrice ()));
    }

    //#4
    private void Exercise_04() {

        bookService.findTitlesForBooksNotPublishedIn (2000)
                .forEach (System.out::println);
    }

    //#3
    private void Exercise_03() {
        List <Book> books = this.bookService.findAllByPrice (5, 40);

        books.forEach (b -> System.out.printf ("%s $%.2f%n", b.getTitle (), b.getPrice ()));
    }

    //#2
    private void Exercise_02() {
        this.bookService.findAllTitlesForGoldenEdition (EditionType.GOLD, 5000)
                .forEach (System.out::println);

    }

    private void Exercise_01() {
        //miNor#1
        String ageRestriction = scanner.nextLine ();
        this.bookService.findAllTitlesByAgeRestriction (ageRestriction)
                .forEach (System.out::println);
    }

    private void pritnALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate (firstName, lastName)
                .forEach (System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks ()
                .forEach (System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear (year)
                .forEach (System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear (year)
                .stream ()
                .map (Book::getTitle)
                .forEach (System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories ();
        authorService.seedAuthors ();
        bookService.seedBooks ();
    }
}
