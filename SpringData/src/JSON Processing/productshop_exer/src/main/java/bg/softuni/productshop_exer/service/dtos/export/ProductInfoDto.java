package bg.softuni.productshop_exer.service.dtos.export;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class ProductInfoDto implements Serializable {


    @Expose
    private String name;


    @Expose
    private BigDecimal price;






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
