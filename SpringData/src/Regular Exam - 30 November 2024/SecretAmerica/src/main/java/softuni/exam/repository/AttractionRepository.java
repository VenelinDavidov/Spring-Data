package softuni.exam.repository;

import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Attraction;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Optional<Attraction> findByName(@Size(min = 5, max = 40) String name);

    @Query("select a from Attraction a " +
            "join Country c on c.id = a.country.id " +
            "join Visitor v on v.attraction.id = a.id " +
            "where a.type like 'historical site' or a.type like 'archaeological site'" +
            "and a.elevation >= 300 " +
            "group by a.id, a.name, c.name " +
            "order by a.name, c.name")
    List<Attraction> findAttractionByTypeAndElevationMoreThanOrEqualTo300();
}

