package bg.softuni.xml_processing_exer.service.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRoodDto implements Serializable {


    @XmlElement(name = "car")
    private List<CarSeedDto> carSeedDtoList;




    // getter and setter

    public List<CarSeedDto> getCarSeedDtoList() {
        return carSeedDtoList;
    }

    public void setCarSeedDtoList(List<CarSeedDto> carSeedDtoList) {
        this.carSeedDtoList = carSeedDtoList;
    }
}
