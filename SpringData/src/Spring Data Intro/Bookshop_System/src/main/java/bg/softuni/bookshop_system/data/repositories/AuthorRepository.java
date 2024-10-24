package bg.softuni.bookshop_system.data.repositories;

import bg.softuni.bookshop_system.data.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository <Author, Integer> {

    //From Authors a Join a.books b Order By b Desc

    Set <Author> findAllByBooksReleaseDateBefore(LocalDate releaseDate);

    //From Authors a Order By a.books.size Desc;
    @Query("FROM Author a ORDER BY SIZE(a.books) DESC")
    Set <Author> findAllByOrderByBooksSizeDesc();

}
