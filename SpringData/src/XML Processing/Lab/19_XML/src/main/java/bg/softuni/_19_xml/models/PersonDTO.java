package bg.softuni._19_xml.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDTO {
    @XmlElement
    private String firstName;

    @XmlElement(name = "lastName")
    private String lastName;

    @XmlAttribute(name = "age")
    private int age;

    public String number = "12093321";

    private AddressDTO address;



    public PersonDTO() {}



    public PersonDTO(String firstName, String lastName, int age, AddressDTO address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
    }


}
