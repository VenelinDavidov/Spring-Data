package bg.softuni.xml_processing_exer.service;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface PartService {
    
    void seedParts() throws JAXBException, FileNotFoundException;
}
