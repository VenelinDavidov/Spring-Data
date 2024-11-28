package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.BookSeedDto;
import softuni.exam.models.dto.xml.BorrowingRecordSeedDTO;
import softuni.exam.models.dto.xml.BorrowingRecordSeedRootDTO;
import softuni.exam.models.dto.xml.LibraryMemberSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.models.entity.enums.Genre;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private static final String FILE_PATH = "src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository, Gson gson, XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        BorrowingRecordSeedRootDTO borrowingRecordSeedRootDTO =
                this.xmlParser.fromFile(FILE_PATH, BorrowingRecordSeedRootDTO.class);

        for (BorrowingRecordSeedDTO borrowingRecordSeedDTO : borrowingRecordSeedRootDTO.getBorrowingRecordSeedDTOList()) {

            BookSeedDto bookSeedDto = borrowingRecordSeedDTO.getBookSeedDto();
            LibraryMemberSeedDto libraryMemberSeedDto = borrowingRecordSeedDTO.getLibraryMemberSeedDto();

            Optional<Book> optionalBook =
                    this.bookRepository
                            .findByTitle(bookSeedDto.getTitle());


            Optional<LibraryMember> optionalLibraryMember =
                    this.libraryMemberRepository
                            .findById(libraryMemberSeedDto.getId());

            if (!this.validationUtil.isValid(borrowingRecordSeedDTO) || optionalBook.isEmpty() || optionalLibraryMember.isEmpty()) {
                sb.append("Invalid borrowing record").append(System.lineSeparator());
                continue;
            }

            BorrowingRecord borrowingRecord = this.modelMapper.map(borrowingRecordSeedDTO, BorrowingRecord.class);
            borrowingRecord.setBook(optionalBook.get());
            borrowingRecord.setMember(optionalLibraryMember.get());
            this.borrowingRecordRepository.saveAndFlush(borrowingRecord);

            sb.append(String.format("Successfully imported borrowing record %s - %s",
                            borrowingRecord.getBook().getTitle(),
                            borrowingRecord.getBorrowDate()))
                    .append(System.lineSeparator());

        }


        return sb.toString().trim();
    }

    @Override
    public String exportBorrowingRecords() {

        LocalDate localDate = LocalDate.parse("2021-09-10");

        return this.borrowingRecordRepository
                .findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(localDate, Genre.SCIENCE_FICTION)
                .stream()
                .map(borrowingRecord -> String.format
                                ("Book title: %s\n" +
                                "*Book author: %s\n" +
                                "**Date borrowed: %s\n" +
                                "***Borrowed by: %s %s\n",
                        borrowingRecord.getBook().getTitle(),
                        borrowingRecord.getBook().getAuthor(),
                        borrowingRecord.getBorrowDate(),
                        borrowingRecord.getMember().getFirstName(),
                        borrowingRecord.getMember().getLastName()))
                .collect(Collectors.joining());

//        Set<BorrowingRecord> allByReturnDateIsNull = borrowingRecordRepository
//                .findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate.parse("2021-09-10"),Genre.SCIENCE_FICTION);
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        allByReturnDateIsNull.forEach(borrowRecord -> {
//            stringBuilder.append(String.format("Book title: %s\n" +
//                                    "*Book author: %s\n" +
//                                    "**Date borrowed: %s\n" +
//                                    "***Borrowed by: %s %s",
//                            borrowRecord.getBook().getTitle(),
//                            borrowRecord.getBook().getAuthor(),
//                            borrowRecord.getBorrowDate().toString(),
//                            borrowRecord.getMember().getFirstName(),
//                            borrowRecord.getMember().getLastName()))
//                    .append(System.lineSeparator());
//        });
//
//        return stringBuilder.toString().trim();
    }
}
