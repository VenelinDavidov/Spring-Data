package bg.softuni.bookshop_system.service.impl;

import bg.softuni.bookshop_system.data.entities.Author;
import bg.softuni.bookshop_system.data.repositories.AuthorRepository;
import bg.softuni.bookshop_system.service.AuthorService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String FILE_PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {

        if (this.authorRepository.count () == 0) {
            Files.readAllLines (Path.of (FILE_PATH))
                    .stream ()
                    .filter (row -> !row.trim ().isEmpty ())
                    .forEach (row -> {
                        //Russell Howard
                        String[] token = row.split ("\\s+");
                        this.authorRepository.saveAndFlush (new Author (token[0], token[1]));

                    });
        }
    }

        @Override
        public Author getRandomAuthor () {
            int randomId = ThreadLocalRandom
                    .current ()
                    .nextInt (1, (int) this.authorRepository.count () + 1);

            return this.authorRepository.findById (randomId).get ();

        }

        //#3
    @Override
    public List <String> getAllAuthorsFirstAndLastNameForBooksBeforeYear1990() {
        return this.authorRepository.findAllByBooksReleaseDateBefore (LocalDate.of (1990, 1, 1))
                .stream ()
                .map (author -> String.format ("%s %s", author.getFirstName (),author.getLastName ()))
                .collect (Collectors.toList ());
    }

    @Override
    public List <String> getAllAuthorsDescBooks() {
        Set <Author> allByOrderByBooksDesc = this.authorRepository.findAllByOrderByBooksSizeDesc ();

        return allByOrderByBooksDesc
                .stream ()
                .map (author -> String.format ("%s %s %d",
                        author.getFirstName (),
                        author.getLastName (),
                        author.getBooks ().size ()))
                .collect (Collectors.toList ());
    }
}
