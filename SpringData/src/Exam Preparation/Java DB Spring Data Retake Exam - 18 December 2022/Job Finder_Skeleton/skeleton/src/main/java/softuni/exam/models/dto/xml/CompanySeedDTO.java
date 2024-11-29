package softuni.exam.models.dto.xml;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedDTO implements Serializable {

    @XmlElement
    @Size(min = 3, max = 40)
    private String companyName;

    @XmlElement(name = "dateEstablished")
    private String dateEstablished;

    @XmlElement
    @Size(min = 3, max = 30)
    private String website;

    @XmlElement(name = "countryId")
    private Long countryId;


}
