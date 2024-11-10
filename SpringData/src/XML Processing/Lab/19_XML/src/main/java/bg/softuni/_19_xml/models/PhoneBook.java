package bg.softuni._19_xml.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneBook {

    private String owner;

    @XmlElementWrapper(name = "numbers-wrapper")
    @XmlElement(name = "numbers-item")
    private List<String> numbers;

    @XmlElementWrapper
    @XmlElement(name = "phoneNumber")
    private List<PhoneNumber> phoneNumbers;


    //constructor
    public PhoneBook() {}


    //full constructor
    public PhoneBook(String owner, List<String> numbers, List<PhoneNumber> phoneNumbers) {
        this.owner = owner;
        this.numbers = numbers;
        this.phoneNumbers = phoneNumbers;
    }
}
