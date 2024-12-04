package softuni.exam.models.dto.xml;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@XmlRootElement(name = "personal_data")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalDateSeedDto implements Serializable {

    @XmlElement
    @Min(1)
    @Max(100)
    private int age;

    @XmlElement(name = "gender")
    private String gender;

    @XmlElement(name = "birth_date")
    private String birthDate;

    @XmlElement(name = "card_number")
    @Size(min = 9, max = 9)
    private String cardNumber;
}
