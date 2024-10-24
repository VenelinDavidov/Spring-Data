package bg.softuni.bookshop_system.data.repositories;

import bg.softuni.bookshop_system.data.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // From Book b Where b.releaseDate > 1?
    Set <Book> findAllByReleaseDateAfter(LocalDate date);

    //
    Set<Book> findAllByAuthorFirstNameAndAuthorLastNameOrderByReleaseDateDescTitle(String firstName, String lastName);
}
