package bg.softuni._19_xml.models;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "address")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressDTO {


    private String country;
    private String city;

    public AddressDTO() {}

    public AddressDTO(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
