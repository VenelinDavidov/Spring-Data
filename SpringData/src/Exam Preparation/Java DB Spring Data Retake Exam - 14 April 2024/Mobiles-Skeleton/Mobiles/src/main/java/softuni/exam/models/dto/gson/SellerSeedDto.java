package softuni.exam.models.dto.gson;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter

public class SellerSeedDto implements Serializable {

    @Expose
    @Size(min = 2, max = 30)
    private String firstName;

    @Expose
    @Size(min = 2, max = 30)
    private String lastName;

    @Expose
    @Size(min = 3, max = 6)
    private String personalNumber;
}
