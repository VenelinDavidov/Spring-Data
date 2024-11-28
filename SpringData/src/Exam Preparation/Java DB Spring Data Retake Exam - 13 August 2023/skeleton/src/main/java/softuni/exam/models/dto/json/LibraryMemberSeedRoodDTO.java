package softuni.exam.models.dto.json;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class LibraryMemberSeedRoodDTO implements Serializable {


    @Expose
    @Size(min = 2, max = 40)
    private String address;
    @Expose
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;
    @Expose
    @Size(min = 2, max = 30)
    private String lastName;
    @Expose
    @Size(min = 2, max = 20)
    private String phoneNumber;
}
