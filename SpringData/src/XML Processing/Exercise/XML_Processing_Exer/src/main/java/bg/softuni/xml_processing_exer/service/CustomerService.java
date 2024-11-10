package bg.softuni.xml_processing_exer.service;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface CustomerService {

    void seedCustomers() throws JAXBException, FileNotFoundException;

    void exportOrderedCustomers() throws JAXBException;

    void exportCustomersWithBoughtCars() throws JAXBException;
}
