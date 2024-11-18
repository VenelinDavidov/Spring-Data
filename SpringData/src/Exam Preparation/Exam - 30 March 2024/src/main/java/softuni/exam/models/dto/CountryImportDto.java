package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;


public class CountryImportDto implements Serializable {

    @Expose
    @Length(min = 3, max = 30)
    private String name;

    @Expose
    @Length(min = 3, max = 30)
    private String capital;






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
