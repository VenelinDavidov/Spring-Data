package bg.softuni.xml_processing_exer.util;

import jakarta.xml.bind.JAXBException;

import java.io.FileNotFoundException;

public interface XmlParser {

    // import
    <E> E parse(Class<E> clazz, String path) throws JAXBException, FileNotFoundException;



    // export
    <E> void exportToFile(Class<E> clazz, E object, String path) throws JAXBException;
}
