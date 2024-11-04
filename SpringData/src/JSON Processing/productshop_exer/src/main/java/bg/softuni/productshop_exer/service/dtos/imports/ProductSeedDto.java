package bg.softuni.productshop_exer.service.dtos.imports;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductSeedDto implements Serializable {

    @Expose
    @NotNull
    @Size(min = 3)
    private String name;

    @Expose
    @NotNull
    private BigDecimal price;



    // getter and setter
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
