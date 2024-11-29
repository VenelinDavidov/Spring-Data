package softuni.exam.models.dto.xml;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@XmlRootElement(name = "job")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobsSeedDTO implements Serializable {


    @XmlElement(name = "jobTitle")
    @Size(min = 2, max = 40)
    private String jobTitle;

    @XmlElement(name = "hoursAWeek")
    @DecimalMin(value = "10.00")
    private double hoursAWeek;

    @XmlElement(name = "salary")
    @DecimalMin(value = "300.00")
    private double salary;

    @XmlElement(name = "description")
    @Size(min = 5)
    private String description;

    @XmlElement(name = "companyId")
    private Long companyId;
}
