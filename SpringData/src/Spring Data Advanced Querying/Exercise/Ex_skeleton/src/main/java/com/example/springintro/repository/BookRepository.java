package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Repository
public interface BookRepository extends JpaRepository <Book, Long> {

    List <Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List <Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List <Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List <Book> findByAgeRestriction(AgeRestriction ageRestriction);

    List <Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    List <Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lowerThan, BigDecimal greaterThan);

    List <Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List <Book> findAllByReleaseDateLessThan(LocalDate date);

    List <Book> findAllByTitleContaining(String needle);

    List <Book> findAllByAuthorLastNameStartingWith(String name);


    @Query("SELECT b.copies FROM Book b " +
            "WHERE b.author.firstName like :aFirstName AND b.author.lastName like :aLastName")
    List <Integer> findTotalCopiesPerAuthor(@Param(value = "aFirstName") String firstName,
                                            @Param(value = "aLastName") String lastName);



    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :min ")
    int CountByTitleLengthGreaterThan(int min);


    @Query("""
            SELECT b.title, b.editionType, b.ageRestriction, b.price
            FROM Book b
            WHERE b.title LIKE :givenTitle
            """)
    List <Object[]> findBookInformationByTitle(@Param("givenTitle") String title);


    //update query
    @Query("UPDATE Book b " +
            "SET b.copies = b.copies + :additionalCopies " +
            "WHERE b.id = :id")
    @Modifying
    @Transactional
    int updateAllByBookCopiesById(int id, int additionalCopies);
}
