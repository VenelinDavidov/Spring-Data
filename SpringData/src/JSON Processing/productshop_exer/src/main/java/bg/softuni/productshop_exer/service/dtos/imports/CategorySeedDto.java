package bg.softuni.productshop_exer.service.dtos.imports;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CategorySeedDto implements Serializable {

    @Expose
    @NotNull
    @Size(min = 3, max = 15)
    private String name;





    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }
}
