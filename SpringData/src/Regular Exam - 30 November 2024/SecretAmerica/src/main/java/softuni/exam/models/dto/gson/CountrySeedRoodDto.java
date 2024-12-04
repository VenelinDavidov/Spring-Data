package softuni.exam.models.dto.gson;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CountrySeedRoodDto implements Serializable {

    @Expose
    @Size(min = 3, max = 40)
    private String name;

    @Expose
    @Positive
    private double area;
}
