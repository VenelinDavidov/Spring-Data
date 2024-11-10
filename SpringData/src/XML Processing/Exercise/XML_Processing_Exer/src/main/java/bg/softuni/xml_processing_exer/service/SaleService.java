package bg.softuni.xml_processing_exer.service;

import jakarta.xml.bind.JAXBException;

public interface SaleService {

    void seedSales();

    void exportSales() throws JAXBException;
}
