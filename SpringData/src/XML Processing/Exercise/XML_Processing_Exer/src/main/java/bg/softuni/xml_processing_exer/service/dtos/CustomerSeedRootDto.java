package bg.softuni.xml_processing_exer.service.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto implements Serializable {


    @XmlElement(name = "customer")
    List<CustomerSeedDto> customerSeedDtoList;





    // getter and setter
    public List<CustomerSeedDto> getCustomerSeedDtoList() {
        return customerSeedDtoList;
    }

    public void setCustomerSeedDtoList(List<CustomerSeedDto> customerSeedDtoList) {
        this.customerSeedDtoList = customerSeedDtoList;
    }
}
