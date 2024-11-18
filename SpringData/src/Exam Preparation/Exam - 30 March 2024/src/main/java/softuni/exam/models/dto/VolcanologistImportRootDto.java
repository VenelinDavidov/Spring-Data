package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistImportRootDto implements Serializable {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistImportDto> volcanologistImportDtoList;





    public List<VolcanologistImportDto> getVolcanologistImportDtoList() {
        return volcanologistImportDtoList;
    }

    public void setVolcanologistImportDtoList(List<VolcanologistImportDto> volcanologistImportDtoList) {
        this.volcanologistImportDtoList = volcanologistImportDtoList;
    }
}
