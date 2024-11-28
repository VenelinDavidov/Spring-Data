package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.json.BookSeedRoodDTO;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.enums.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final String FILE_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;


    public BookServiceImpl(BookRepository bookRepository, Gson gson, ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }



    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importBooks() throws IOException {
        StringBuilder result = new StringBuilder();

        BookSeedRoodDTO[] bookSeedRoodDTOS = this.gson.fromJson(readBooksFromFile(), BookSeedRoodDTO[].class);

        for (BookSeedRoodDTO bookSeedRoodDTO : bookSeedRoodDTOS) {

            Optional<Book> optionalBook =
                    this.bookRepository
                            .findBookByTitle(bookSeedRoodDTO.getTitle());

            if(!this.validationUtil.isValid(bookSeedRoodDTO) || optionalBook.isPresent()) {
                result.append("Invalid book").append(System.lineSeparator());
                continue;
            }

            Book book = this.modelMapper.map(bookSeedRoodDTO, Book.class);
//            Genre genre = Genre.valueOf(bookSeedRoodDTO.getGenre().toString());
//            book.setGenre(genre);
            book = this.bookRepository.saveAndFlush(book);


            result.append(String.format("Successfully imported book %s - %s",
                    book.getAuthor(), book.getTitle()))
                    .append(System.lineSeparator());
        }


        return result.toString().trim();
    }
}
