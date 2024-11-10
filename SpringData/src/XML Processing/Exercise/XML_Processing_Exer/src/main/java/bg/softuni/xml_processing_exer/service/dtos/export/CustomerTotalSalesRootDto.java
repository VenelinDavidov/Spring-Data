package bg.softuni.xml_processing_exer.service.dtos.export;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesRootDto implements Serializable {


    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> customerTotalSalesDto;







    public List<CustomerTotalSalesDto> getCustomerTotalSalesDto() {
        return customerTotalSalesDto;
    }

    public void setCustomerTotalSalesDto(List<CustomerTotalSalesDto> customerTotalSalesDto) {
        this.customerTotalSalesDto = customerTotalSalesDto;
    }
}
