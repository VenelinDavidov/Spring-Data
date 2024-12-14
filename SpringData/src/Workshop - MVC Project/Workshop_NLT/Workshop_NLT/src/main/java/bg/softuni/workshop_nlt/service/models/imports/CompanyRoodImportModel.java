package bg.softuni.workshop_nlt.service.models.imports;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;





import java.util.List;

@Getter
@NoArgsConstructor

@JacksonXmlRootElement(localName = "companies")
public class CompanyRoodImportModel {

    @JacksonXmlProperty(localName = "company")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<CompanyImportModel> companyImportModels;


}
