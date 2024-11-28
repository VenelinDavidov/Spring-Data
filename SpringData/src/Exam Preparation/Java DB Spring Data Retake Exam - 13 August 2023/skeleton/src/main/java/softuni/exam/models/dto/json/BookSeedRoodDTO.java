package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import softuni.exam.models.entity.enums.Genre;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class BookSeedRoodDTO implements Serializable {


    @Expose
    @Size(min = 3, max = 40)
    private String author;
    @Expose
    private boolean available;
    @Expose
    @Size(min = 5)
    private String description;
    @Expose
    private Genre genre;
    @Expose
    @Size(min = 3, max = 40)
    private String title;
    @Expose
    @Positive
    private double rating;
}
