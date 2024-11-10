package bg.softuni.xml_processing_exer.service.dtos.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name= "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleDiscountsRootDto implements Serializable {

    @XmlElement(name = "sale")
    private List<SaleDiscountsDto> saleDiscountsDto;




    public List<SaleDiscountsDto> getSaleDiscountsDto() {
        return saleDiscountsDto;
    }

    public void setSaleDiscountsDto(List<SaleDiscountsDto> saleDiscountsDto) {
        this.saleDiscountsDto = saleDiscountsDto;
    }
}
