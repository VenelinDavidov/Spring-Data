package softuni.exam.models.dto.gson;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
public class SaleSeedDto implements Serializable {

    @Expose
    private boolean discounted;

    @Expose
    @Size(min = 7, max = 7 )
    private String number;

    @Expose
    private String saleDate;

    @Expose
    private Long seller;

}
