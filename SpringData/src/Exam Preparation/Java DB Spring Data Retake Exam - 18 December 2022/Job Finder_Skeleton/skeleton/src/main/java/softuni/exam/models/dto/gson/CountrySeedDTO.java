package softuni.exam.models.dto.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.io.Serializable;
@Getter
@Setter
public class CountrySeedDTO implements Serializable {


    @Expose
    @Size(min = 2, max = 30)
    private String name;

    @Expose
    @SerializedName("countryCode")
    @Size(min = 2, max = 19)
    private String code;

    @Expose
    @Size(min = 2, max = 19)
    private String currency;
}
