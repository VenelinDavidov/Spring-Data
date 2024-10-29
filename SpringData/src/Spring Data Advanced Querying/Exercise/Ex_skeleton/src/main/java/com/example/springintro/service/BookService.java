package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllTitlesForGoldenEdition(EditionType editionType, int copies);

    List<Book> findAllByPrice(int lowerBound, int upperBound);

    List<String> findTitlesForBooksNotPublishedIn(int year);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<String> findTitlesContaining(String needle);

    List<String> findTitlesForAuthorNameStartingWith(String lastNameStart);

    int findTitleCountLongerThan(int minLength);

   List<String> findBookInformationForGivenTitle(String title);


    void sellCopies(int bookId, int copiesSold);

    int findTotalBookCopies(String author);
}
