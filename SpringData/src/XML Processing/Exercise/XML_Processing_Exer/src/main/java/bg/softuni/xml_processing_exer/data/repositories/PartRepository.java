package bg.softuni.xml_processing_exer.data.repositories;

import bg.softuni.xml_processing_exer.data.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
}
