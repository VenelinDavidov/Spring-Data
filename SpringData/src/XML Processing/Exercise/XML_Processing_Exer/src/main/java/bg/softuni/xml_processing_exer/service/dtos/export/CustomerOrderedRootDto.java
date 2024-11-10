package bg.softuni.xml_processing_exer.service.dtos.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerOrderedRootDto implements Serializable {


    @XmlElement(name = "customers")
    private List<CustomerOrderedDto> customerOrderedDtoList;





    // getter and setter
    public List<CustomerOrderedDto> getCustomerOrderedDtoList() {
        return customerOrderedDtoList;
    }

    public void setCustomerOrderedDtoList(List<CustomerOrderedDto> customerOrderedDtoList) {
        this.customerOrderedDtoList = customerOrderedDtoList;
    }
}
