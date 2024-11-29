package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Job;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {




    List<Job> findJobsBySalaryIsGreaterThanEqualAndHoursAWeekLessThanEqualOrderBySalaryDesc(double salary, double hoursPerWeek);
}
