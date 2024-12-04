package softuni.exam.models.dto.gson;

import com.google.gson.annotations.Expose;

import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AttractionsSeedRoodDto implements Serializable {

    @Expose
    @Size(min = 5, max = 40)
    private String name;

    @Expose
    @Size(min = 10, max = 100)
    private String description;

    @Expose
    @Size(min = 3, max = 30)
    private String type;

    @Expose
    @PositiveOrZero
    private int elevation;

    @Expose
    private Long country;
}
