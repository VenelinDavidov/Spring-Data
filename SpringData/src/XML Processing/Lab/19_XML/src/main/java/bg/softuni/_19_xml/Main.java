package bg.softuni._19_xml;

import bg.softuni._19_xml.models.AddressDTO;
import bg.softuni._19_xml.models.PersonDTO;
import bg.softuni._19_xml.models.PhoneBook;
import bg.softuni._19_xml.models.PhoneNumber;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Main implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        JAXBContext personContext = JAXBContext.newInstance(PersonDTO.class);
        Marshaller personMarshaller = personContext.createMarshaller();
        personMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        AddressDTO addressDTO = new AddressDTO("BG", "Stara Zagora");
        PersonDTO person = new PersonDTO("First", "Last", 12, addressDTO);
        personMarshaller.marshal(person, System.out);

//        Unmarshaller personUnmarshaller = personContext.createUnmarshaller();
//        PersonDTO parsed = (PersonDTO) personUnmarshaller.unmarshal(System.in);

        // list
        JAXBContext bookContext = JAXBContext.newInstance(PhoneBook.class);
        Marshaller bookMarshaller = bookContext.createMarshaller();
        bookMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        PhoneNumber number1 = new PhoneNumber("Gosho", "123123");
        PhoneNumber number2 = new PhoneNumber("Pesho", "nandsa");
        PhoneNumber number3 = new PhoneNumber("Alex", "!@(#*!@");


        PhoneBook book = new PhoneBook(
                "Pesho",
                List.of("First", "Second", "third"),
                List.of(number1, number2, number3)
        );

        bookMarshaller.marshal(book, System.out);

        Unmarshaller bookUnmarshaller = bookContext.createUnmarshaller();
        PhoneBook parsedBook = (PhoneBook) bookUnmarshaller.unmarshal(System.in);
        System.out.println();
    }
}
