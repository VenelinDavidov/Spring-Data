package softuni.exam.models.dto.xml;

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
@XmlRootElement(name = "visitor")
@XmlAccessorType(XmlAccessType.FIELD)
public class VisitorSeedDto implements Serializable {


    @XmlElement(name = "first_name")
    @Size(min = 2, max = 20)
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2, max = 20)
    private String lastName;

    @XmlElement(name = "attraction_id")
    private Long attractionId;

    @XmlElement(name = "country_id")
    private Long countryId;

    @XmlElement(name = "personal_data_id")
    private Long personalDataId;
}
