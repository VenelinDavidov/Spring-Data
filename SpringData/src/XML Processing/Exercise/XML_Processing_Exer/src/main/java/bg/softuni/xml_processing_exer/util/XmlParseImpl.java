package bg.softuni.xml_processing_exer.util;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;

@Component
public class XmlParseImpl implements XmlParser {

   // import

    @Override
    public <E> E parse(Class<E> clazz, String path) throws JAXBException, FileNotFoundException {

        JAXBContext context = JAXBContext.newInstance(clazz);

        Unmarshaller unmarshaller = context.createUnmarshaller();

//        Unmarshaller unmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();

//        E object = (E) unmarshaller.unmarshal(new FileReader(new File(path)));

        E object = (E) unmarshaller.unmarshal(new File(path));

        return object;
    }



    // export

    @Override
    public <E> void exportToFile(Class<E> clazz, E object, String path) throws JAXBException {

         JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

         Marshaller marshaller = jaxbContext.createMarshaller();
         marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

         marshaller.marshal(object, new File(path));
     }




//
//    @Override
//    public <E> void exportToFile(E object, String path) throws JAXBException {
//
//        JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
//
//        Marshaller marshaller = jaxbContext.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//        marshaller.marshal(object, new File(path));
//    }

}
