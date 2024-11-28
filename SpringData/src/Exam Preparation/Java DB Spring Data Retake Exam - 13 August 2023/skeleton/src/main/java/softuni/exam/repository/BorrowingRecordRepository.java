package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.enums.Genre;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowingRecordRepository  extends JpaRepository<BorrowingRecord, Long> {

    List<BorrowingRecord> findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate borrowDate, Genre genre);
}
