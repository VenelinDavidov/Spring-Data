package bg.softuni.xml_processing_exer.service;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface SupplierService {

    void  seedSuppliers() throws JAXBException, FileNotFoundException;

    // export

   void exportLocalSuppliers() throws JAXBException, FileNotFoundException;
}
