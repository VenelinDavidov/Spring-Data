package bg.softuni._19_xml.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "person")
public class ExamplePerson {
    @XmlElementWrapper(name = "phoneNumbers")
    @XmlElement(name = "phoneNumber")
    List<ExamplePhoneNumber> numbers;
}
