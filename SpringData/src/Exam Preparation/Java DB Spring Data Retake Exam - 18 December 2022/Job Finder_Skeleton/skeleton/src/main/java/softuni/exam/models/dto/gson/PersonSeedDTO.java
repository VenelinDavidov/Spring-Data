package softuni.exam.models.dto.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class PersonSeedDTO implements Serializable {


    @Expose
    @Email
    private String email;

    @Expose
    @Size(min = 2, max = 30)
    private String firstName;

    @Expose
    @Size(min = 2, max = 30)
    private String lastName;

    @Expose
    @Size(min = 2, max = 13)
    private String phone;

    @Expose
    private String statusType;

    @Expose
    private long country;

}
