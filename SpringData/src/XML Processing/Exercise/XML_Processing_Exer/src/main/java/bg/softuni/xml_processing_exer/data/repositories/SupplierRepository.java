package bg.softuni.xml_processing_exer.data.repositories;

import bg.softuni.xml_processing_exer.data.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {


    Set<Supplier> findAllByIsImporter(boolean isImporter);



}
