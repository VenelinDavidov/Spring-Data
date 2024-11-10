package bg.softuni.xml_processing_exer.service.dtos.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierLocalRoodDto implements Serializable {


    @XmlElement(name = "supplier")
    List<SupplierLocalDto> supplierLocalDtoList;




    public List<SupplierLocalDto> getSupplierLocalDtoList() {
        return supplierLocalDtoList;
    }

    public void setSupplierLocalDtoList(List<SupplierLocalDto> supplierLocalDtoList) {
        this.supplierLocalDtoList = supplierLocalDtoList;
    }
}
