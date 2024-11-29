package softuni.exam.models.dto.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "companies")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanySeedRoodDTO implements Serializable {

    @XmlElement(name = "company")
    List<CompanySeedDTO> companySeedDTOList;
}