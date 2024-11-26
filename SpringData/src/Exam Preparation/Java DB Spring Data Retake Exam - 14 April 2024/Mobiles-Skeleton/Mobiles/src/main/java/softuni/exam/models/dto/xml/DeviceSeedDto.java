package softuni.exam.models.dto.xml;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@Setter
@XmlRootElement(name = "device")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceSeedDto implements Serializable {


    @XmlElement(name = "brand")
    @Size(min = 2, max = 20)
    private String brand;

    @XmlElement(name = "device_type")
    private String deviceType;

    @XmlElement(name = "model")
    @Size(min = 1, max = 20)
    private String model;

    @XmlElement
    private double price;

    @XmlElement
    private int storage;

    @XmlElement(name = "sale_id")
    private Long saleId;
}
