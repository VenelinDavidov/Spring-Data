package bg.softuni._19_xml.models;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class PhoneNumber {


    private String name;


    @XmlTransient
    private String number;

    public PhoneNumber() {}

    public PhoneNumber(String name, String number) {
        this.name = name;
        this.number = number;
    }
}
