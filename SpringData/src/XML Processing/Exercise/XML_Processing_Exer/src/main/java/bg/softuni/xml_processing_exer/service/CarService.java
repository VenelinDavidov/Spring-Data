package bg.softuni.xml_processing_exer.service;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface CarService {

    // import
    void seedCars() throws JAXBException, FileNotFoundException;


    // export
    void exportToyotaCars() throws JAXBException, FileNotFoundException;

    void exportCarsAndParts() throws JAXBException, FileNotFoundException;
}
