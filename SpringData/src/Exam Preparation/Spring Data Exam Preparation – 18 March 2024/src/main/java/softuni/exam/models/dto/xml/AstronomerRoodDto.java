package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "astronomers")
@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerRoodDto implements Serializable {

    @XmlElement(name = "astronomer")
    private List<AstronomerSeedDto> astronomerSeeds;





    public List<AstronomerSeedDto> getAstronomerSeeds() {
        return astronomerSeeds;
    }

    public void setAstronomerSeeds(List<AstronomerSeedDto> astronomerSeeds) {
        this.astronomerSeeds = astronomerSeeds;
    }
}
