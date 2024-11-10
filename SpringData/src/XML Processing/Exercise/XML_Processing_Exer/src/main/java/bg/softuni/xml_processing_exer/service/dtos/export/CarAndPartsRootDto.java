package bg.softuni.xml_processing_exer.service.dtos.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarAndPartsRootDto implements Serializable {

    @XmlElement(name = "car")
    private List<CarAndPartDto> carAndPartDtos;




    public List<CarAndPartDto> getCarAndPartDtos() {
        return carAndPartDtos;
    }

    public void setCarAndPartDtos(List<CarAndPartDto> carAndPartDtos) {
        this.carAndPartDtos = carAndPartDtos;
    }
}
