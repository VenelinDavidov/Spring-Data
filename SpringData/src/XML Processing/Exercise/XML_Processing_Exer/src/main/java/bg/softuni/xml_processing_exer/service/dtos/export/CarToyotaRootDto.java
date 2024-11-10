package bg.softuni.xml_processing_exer.service.dtos.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarToyotaRootDto implements Serializable {

    @XmlElement(name = "car")
    List<CarToyotaDto> carToyotaDtoList;





    // getter and setter
    public List<CarToyotaDto> getCarToyotaDtoList() {
        return carToyotaDtoList;
    }

    public void setCarToyotaDtoList(List<CarToyotaDto> carToyotaDtoList) {
        this.carToyotaDtoList = carToyotaDtoList;
    }
}
